package com.example.golden_years

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthenticationViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
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
            .addOnFailureListener { e ->
                error(e.message ?: "failed to login. please try again.")
            }
    }

    fun signOut(){
        auth.signOut()
    }

}
