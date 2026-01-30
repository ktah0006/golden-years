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
    LOGIN("login", "Login", Icons.Default.Login),
//    SIGNUP("signup", "Signup", Icons.Default.Person),
//    ADDENTRY("addEntry", "Add Data", Icons.Default.AddCircle),
    RECORD("record", "Records", Icons.Default.ListAlt),
    REPORT("report", "Charts", Icons.Default.BarChart),
    PROFILE("profile", "Profile", Icons.Default.Person)
}