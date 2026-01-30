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
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SignupScreen() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmedPassword by remember { mutableStateOf("") }
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
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Create an Account",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("name") }
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("email") }
            )

            DisplayDatePicker(
                modifier = Modifier.fillMaxWidth(0.68f),
                label = "DoB"
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("password") }
            )

            OutlinedTextField(
                value = confirmedPassword,
                onValueChange = { confirmedPassword = it },
                label = { Text("confirm password") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "must contain at least 8 characters",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Sign up")
            }

            Spacer(modifier = Modifier.height(2.dp))

            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Already have an account?")
            }
        }
    }
}

