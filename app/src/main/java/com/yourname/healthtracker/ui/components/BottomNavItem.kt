package com.yourname.healthtracker.ui.components

import android.R
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Stats: Screen("stats")
    object Settings: Screen("settings")
}

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

