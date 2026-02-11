package com.example.golden_years

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import java.util.Date


@Composable
fun SignupScreen(
    navController: NavController,
    authenticationViewModel: AuthenticationViewModel= viewModel()
) {
    var name by remember { mutableStateOf("") }
    var selectedDob by remember { mutableStateOf<Long?>(null) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmedPassword by remember { mutableStateOf("") }
    var signUpError by remember { mutableStateOf<String?>(null) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    fun handleSignUp(){
        if (selectedDob == null){
            signUpError = "Please select date of birth"
            return
        }
        val dobToStore = Timestamp(Date(selectedDob!!))

        if (password != confirmedPassword) {
            signUpError = "Passwords do not match"
            return
        }

        authenticationViewModel.signUp(
            email = email,
            password = password,
            name = name,
            dob = dobToStore,
            error = { message ->
                signUpError = message
            },
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                        .padding(paddingValues)
                        .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                "Create an Account",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(24.dp))
            signUpError?.let {
                Text(
                    text = it,
                    modifier = Modifier.fillMaxWidth(0.7f),
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("name *") }
            )

            DisplayDatePicker(
                selectedDate = selectedDob,
                onDateSelected = { selectedDob = it },
                modifier = Modifier.fillMaxWidth(0.68f),
                label = "DoB *"
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("email *") }
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    signUpError = null},
                label = { Text("password *") },
                // show/hide password
                visualTransformation =
                    if (isPasswordVisible){
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                trailingIcon = {
                    IconButton(
                        onClick = {isPasswordVisible = !isPasswordVisible}
                    ) {
                        Icon(
                            imageVector = if (isPasswordVisible)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff,
                            contentDescription = "show/hide password"
                        )
                    }
                }
            )

            OutlinedTextField(
                value = confirmedPassword,
                onValueChange = {
                    confirmedPassword = it
                    signUpError = null},
                label = { Text("confirm password *")},
                visualTransformation =
                    if (isConfirmPasswordVisible){
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                trailingIcon = {
                    IconButton(
                        onClick = {isConfirmPasswordVisible = !isConfirmPasswordVisible}
                    ) {
                        Icon(
                            imageVector = if (isConfirmPasswordVisible)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff,
                            contentDescription = "show/hide password"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { handleSignUp() },
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Sign up")
            }

            Spacer(modifier = Modifier.height(2.dp))

            OutlinedButton(
                onClick = {
                    navController.navigate(AuthenticationDestinations.LOGIN.route)
                },
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Already have an account?")
            }
        }
    }
}



