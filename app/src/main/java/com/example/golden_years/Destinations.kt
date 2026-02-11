package com.example.golden_years

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.filled.*

enum class Destinations(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    HOME("home", "Home", Icons.Default.Home),
    RECORD("record", "Records", Icons.Default.ListAlt),
    REPORT("report", "Charts", Icons.Default.BarChart),
    PROFILE("profile", "Profile", Icons.Default.Person),
}

enum class AuthenticationDestinations(
    val route: String,
    val label: String,
) {
    LOGIN("login", "Login"),
    SIGNUP("signup", "Signup"),
}

enum class OtherDestinations(
    val route: String,
    val label: String,
) {
    ADDENTRY("addEntry", "Add Data"),
    VERIFICATION("forgotPasswordVerification", "Verify Email"),
    EDITRECORD("editRecord", "Edit Existing Record")
}