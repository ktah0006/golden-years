package com.example.golden_years
import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController,
    authenticationViewModel: AuthenticationViewModel = viewModel()
) {
    val currUserName by authenticationViewModel.currentUserName.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.7f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.height(26.dp))

            Text(
                "Hello, ${currUserName ?: "Loading..."}!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondary
            )

            // add weather logic
            Image(
                painter = painterResource(id = R.drawable.w),
                contentDescription = "temporary weather image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Text(
                "The weather looks good for a walk",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(26.dp))

            Button(
                onClick = {
                    navController.navigate(OtherDestinations.ADDENTRY.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("+ Add New Entry")
            }
            Button(
                onClick = {
                    navController.navigate(Destinations.RECORD.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View all BP and Glucose Records")
            }
            Button(
                onClick = {
                    navController.navigate(Destinations.REPORT.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Charts")
            }

        }
    }
}