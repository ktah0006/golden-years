package com.example.golden_years

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthenticationViewModel (application: Application) : AndroidViewModel(application) {


    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val recordRepository = RecordRepository(application)
    var currentUser by mutableStateOf(auth.currentUser)

    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        currentUser = firebaseAuth.currentUser
    }

    init {
        auth.addAuthStateListener(authStateListener)
    }

    override fun onCleared() {
        auth.removeAuthStateListener(authStateListener)
        super.onCleared()
    }

    fun signIn(
        email: String,
        password: String,
        error: (String) -> Unit
    ) {

        if (email.isBlank() || password.isBlank()) {
            error("email and/or password are empty")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                viewModelScope.launch {
                    recordRepository.getRecordsFirestore(
                        it.user!!.uid
                    )
                }
            }
            .addOnFailureListener { e ->
                error(e.message ?: "failed to login. please try again.")
            }
    }
        fun signOut() {
            viewModelScope.launch {
                recordRepository.clearAllRecords()
            }
            auth.signOut()
        }

        fun signUp(
            email: String,
            password: String,
            name: String,
            dob: Timestamp,
            error: (String) -> Unit,
        ) {
            if (email.isBlank() || password.isBlank() || name.isBlank()) {
                error("all fields are required")
                return
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val uid = result.user!!.uid

                    val newUserMap = mapOf(
                        "name" to name,
                        "email" to email,
                        "dob" to dob,
                        "createdAt" to FieldValue.serverTimestamp()
                    )

                    db.collection("users")
                        .document(uid)
                        .set(newUserMap)
                        .addOnFailureListener { e ->
                            error("Account created but failed to save profile")
                        }
                }
                .addOnFailureListener { e ->
                    error(e.message ?: "failed to create an account. please try again.")
                }
        }

    }
