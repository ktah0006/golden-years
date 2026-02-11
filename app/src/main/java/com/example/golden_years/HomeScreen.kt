package com.example.golden_years
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.golden_years.weather.WeatherViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    authenticationViewModel: AuthenticationViewModel = viewModel(),
    weatherViewModel: WeatherViewModel = viewModel()
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


            // location permission
            val weather = weatherViewModel.weather
            val currContext = LocalContext.current

            val permissionRequest = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { granted ->
                    if (granted) {
                        weatherViewModel.getWeather(currContext)
                    } else {
                        Log.e("WEATHER", "Location permission denied")
                    }
                }

            LaunchedEffect(Unit) {
                permissionRequest.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }

            // weather information from openweather api
            if (weather == null) {
                Text("Loading...")
            } else {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "It's ${weather.main.temp}°C in ${weather.name} " +
                    "current condition: ${weather.weather.first().description}",
                    modifier = Modifier.fillMaxWidth(0.8f),
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 30.sp

                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // main navigation
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