package com.example.golden_years

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

enum class Destinations(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    HOME("home", "Home", Icons.Default.Home),
    LOGIN("login", "Login", Icons.Default.Info),
    SIGNUP("signup", "Signup", Icons.Default.AccountCircle),
    ADDENTRY("addEntry", "AddEntry", Icons.Default.Home),
    REPORT("report", "Report", Icons.Default.Info),
    RECORD("record", "Record", Icons.Default.AccountCircle)
}