package com.example.golden_years

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val authenticationViewModel: AuthenticationViewModel = viewModel()
    val user = authenticationViewModel.currentUser
    LaunchedEffect(user) {
        if (user != null) {
            navController.navigate(Destinations.HOME.route) {
                popUpTo(0)
            }
        } else {
            navController.navigate(AuthenticationDestinations.LOGIN.route) {
                popUpTo(0)
            }
        }
    }

    Scaffold(
        bottomBar = {
            if (user != null) {
                NavigationBar(
                    modifier = Modifier.padding(bottom = 20.dp),
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
// iterate through enum values
                    Destinations.entries.forEach { destination ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    destination.icon, contentDescription =
                                        destination.label
                                )
                            },
                            label = { Text(destination.label) },
                            selected = currentDestination?.route == destination.route,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.tertiary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurface
                            ),
                            onClick = {
                                navController.navigate(destination.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = AuthenticationDestinations.LOGIN.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(AuthenticationDestinations.LOGIN.route) { LoginScreen(navController) }
            composable(AuthenticationDestinations.SIGNUP.route) { SignupScreen(navController) }
            composable(Destinations.HOME.route) { HomeScreen(navController) }
            composable(Destinations.RECORD.route) { RecordScreen() }
            composable(Destinations.REPORT.route) { ReportScreen() }
            composable(Destinations.PROFILE.route) { ProfileScreen() }
            composable(OtherDestinations.ADDENTRY.route) { AddEntry(navController) }
            composable(OtherDestinations.VERIFICATION.route) { ForgotPasswordVerification(navController) }
            composable(OtherDestinations.RESETPASSWORD.route) { ResetPassword() }

        }
    }
}