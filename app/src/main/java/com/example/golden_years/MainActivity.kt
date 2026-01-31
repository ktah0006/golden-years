package com.example.golden_years

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.golden_years.ui.theme.GoldenyearsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoldenyearsTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//                BottomNavigationBar()
                AppNavigation()
//                SuccessScreen()
//                ForgotPasswordVerification()
//                ResetPassword()
//                LoginScreen()

//                SignupScreen()
//                AddEntry()

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BottomNavigationBar() {
//    val navController = rememberNavController()
////    val loggedIn = com.google.firebase.auth.FirebaseAuth
////        .getInstance()
////        .currentUser != null
//
//    val authenticationViewModel: AuthenticationViewModel = viewModel()
//    val user = authenticationViewModel.currentUser
//    LaunchedEffect(user) {
//        if (user != null) {
//            navController.navigate(Destinations.HOME.route) {
//                popUpTo(0)
//            }
//        } else {
//            navController.navigate(AuthenticationDestinations.LOGIN.route) {
//                popUpTo(0)
//            }
//        }
//    }
//
//    Scaffold(
//        bottomBar = {
//            if (user != null) {
//                NavigationBar(
//                    modifier = Modifier.padding(bottom = 20.dp),
//                    containerColor = MaterialTheme.colorScheme.surface
//                ) {
//                    val navBackStackEntry by navController.currentBackStackEntryAsState()
//                    val currentDestination = navBackStackEntry?.destination
//// iterate through enum values
//                    Destinations.entries.forEach { destination ->
//                        NavigationBarItem(
//                            icon = {
//                                Icon(
//                                    destination.icon, contentDescription =
//                                        destination.label
//                                )
//                            },
//                            label = { Text(destination.label) },
//                            selected = currentDestination?.route == destination.route,
//                            colors = NavigationBarItemDefaults.colors(
//                                selectedIconColor = MaterialTheme.colorScheme.primary,
//                                selectedTextColor = MaterialTheme.colorScheme.primary,
//                                indicatorColor = MaterialTheme.colorScheme.tertiary,
//                                unselectedIconColor = MaterialTheme.colorScheme.onSurface,
//                                unselectedTextColor = MaterialTheme.colorScheme.onSurface
//                            ),
//                            onClick = {
//                                navController.navigate(destination.route) {
//                                    popUpTo(navController.graph.findStartDestination().id) {
//                                        saveState = true
//                                    }
//                                    launchSingleTop = true
//                                    restoreState = true
//                                }
//                            }
//                        )
//                    }
//                }
//            }
//        }
//    ) { paddingValues ->
//        NavHost(
//            navController = navController,
//////            SHOULD DO THIS LATER
//////            startDestination = Destinations.LOGIN.route,
////            startDestination = Destinations.HOME.route,
////            startDestination = if (loggedIn) {
////                Destinations.HOME.route
////            }
////            else {
////                AuthenticationDestinations.LOGIN.route
////            },
//            startDestination = AuthenticationDestinations.LOGIN.route,
//            modifier = Modifier.padding(paddingValues)
//        ) {
//            composable(AuthenticationDestinations.LOGIN.route) { LoginScreen() }
//            composable(AuthenticationDestinations.SIGNUP.route) { SignupScreen() }
//            composable(Destinations.HOME.route) { HomeScreen() }
////            composable(AuthenticationDestinations.LOGIN.route) { LoginScreen() }
////            composable(Destinations.SIGNUP.route) { SignupScreen() }
////            composable(Destinations.ADDENTRY.route) { AddEntry() }
//            composable(Destinations.RECORD.route) { RecordScreen() }
//            composable(Destinations.REPORT.route) { ReportScreen() }
//            composable(Destinations.PROFILE.route) { ProfileScreen() }
//         }
//    }
//
//}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GoldenyearsTheme {
        Greeting("Android")
    }
}