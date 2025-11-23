package com.yourname.healthtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.yourname.healthtracker.data.FoodViewModel
import com.yourname.healthtracker.data.MainRepository
import com.yourname.healthtracker.data.MainRepositoryImpl
import com.yourname.healthtracker.di.AppModule
import com.yourname.healthtracker.screen.MainScreen
import com.yourname.healthtracker.screen.SettingsScreen
import com.yourname.healthtracker.screen.StatsScreen
import com.yourname.healthtracker.ui.components.BottomNavigationBar
import com.yourname.healthtracker.ui.components.Screen
import com.yourname.healthtracker.ui.theme.HealthTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HealthTrackerTheme {
                val navController = rememberNavController()
//                val repository: MainRepository = AppModule.provideMainRepository()
                val foodVM: FoodViewModel = viewModel()
                val repository: MainRepository = AppModule.provideMainRepository()

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Screen.Home.route) {
                            MainScreen(foodVM)
                        }
                        composable(route = Screen.Stats.route) {
                            StatsScreen(foodVM, repository)
                        }
                        composable(route = Screen.Settings.route) {
                            SettingsScreen()
                        }
                    }
                }
            }
        }
    }
}