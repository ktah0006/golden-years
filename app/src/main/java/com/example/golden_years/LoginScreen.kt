package com.example.golden_years

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun LoginScreen(
    navController: NavController,
    authenticationViewModel: AuthenticationViewModel= viewModel()
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf<String?>(null) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo image",
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))
        Text(
            "Log in to your account",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(18.dp))
        loginError?.let {
            Text(
                text = it,
                modifier = Modifier.fillMaxWidth(0.7f),
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                loginError = null },
            label = { Text("email") }
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                loginError = null},
            label = { Text("password") },
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

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {

                authenticationViewModel.signIn(
                    email = email,
                    password = password,
                    error = { message ->
                        loginError = message
                    },
                )
            },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Sign in")
        }

        Spacer(modifier = Modifier.height(2.dp))
        OutlinedButton(
            onClick = {
                navController.navigate(AuthenticationDestinations.SIGNUP.route)
            },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Create Account")
        }


        Spacer(modifier = Modifier.height(10.dp))
        TextButton(
            onClick = { navController.navigate(OtherDestinations.VERIFICATION.route) },
        ) {
            Text(
                text = "forgot password",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        }
    }
        }

}