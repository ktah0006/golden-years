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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@Composable
fun ForgotPasswordVerification(
    navController: NavController,
    authenticationViewModel: AuthenticationViewModel= viewModel()
) {
    var email by remember { mutableStateOf("") }
    var resetError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(top = 64.dp)
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    "Reset Password",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    "Enter your email address",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.height(16.dp))

                resetError?.let {
                    Text(
                        text = it,
                        modifier = Modifier.fillMaxWidth(0.7f),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it
                        resetError = null},
                    label = { Text("email") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Instructions to reset your password will be sent to this email",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.fillMaxWidth(0.7f)
                )


                val context = LocalContext.current
                Spacer(modifier = Modifier.height(52.dp))
                Button(
                    onClick = {
                        authenticationViewModel.resetPassword(
                            email = email,
                            onSuccess = {
                                navController.navigate(AuthenticationDestinations.LOGIN.route)
                                Toast.makeText(
                                    context,
                                    "email sent",
                                    Toast.LENGTH_LONG
                                ).show()
                            },
                            error = { message ->
                                resetError = message
                            }
                        )
                    },
                ) {
                    Text("send reset email")
                }

            }
        }
    }
}