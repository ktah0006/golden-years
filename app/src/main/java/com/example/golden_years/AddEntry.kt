package com.example.golden_years

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
fun AddEntry() {
    var systolicBloodPressure by remember { mutableStateOf("") }
    var diastolicBloodPressure by remember { mutableStateOf("") }
    var glucose by remember { mutableStateOf("") }
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "logo image",
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("New Record",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(60.dp))
        Column(
            modifier = Modifier.fillMaxWidth(0.7f),
            horizontalAlignment = Alignment.Start
        ) {
            Text("Blood Pressure",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
            OutlinedTextField(
                value = systolicBloodPressure,
                onValueChange = { systolicBloodPressure = it },
                label = { Text("Systolic BP") }
            )

            OutlinedTextField(
                value = diastolicBloodPressure,
                onValueChange = { diastolicBloodPressure = it },
                label = { Text("Diastolic BP") }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Glucose",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedTextField(
                value = glucose,
                onValueChange = { glucose = it },
                label = { Text("glucose BP") }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("+ Add New Record")
        }
    }
}