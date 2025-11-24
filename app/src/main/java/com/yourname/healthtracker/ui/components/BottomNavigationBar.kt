package com.yourname.healthtracker.ui.components

import com.yourname.healthtracker.R
import android.provider.Settings.Global.getString
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(
            title = stringResource(R.string.home),
            icon = Icons.Default.Home,
            route = Screen.Home.route
        ),
        BottomNavItem(
            title = stringResource(R.string.stats),
            icon = Icons.Default.Info,
            route = Screen.Stats.route
        ),
        BottomNavItem(
            title = stringResource(R.string.settings),
            icon = Icons.Default.Settings,
            route = Screen.Settings.route
        ),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach {
            item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                },
                icon = {
                    Icon(
                      imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }

}