package com.yourname.healthtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.yourname.healthtracker.data.repository.DaysRepository
import com.yourname.healthtracker.presentation.viewmodel.FoodViewModel
import com.yourname.healthtracker.data.repository.MainRepository
import com.yourname.healthtracker.data.repository.ProfileRepository
import com.yourname.healthtracker.data.local.AppDatabase
import com.yourname.healthtracker.data.local.dao.DaysDao
import com.yourname.healthtracker.data.local.dao.UserProfileDao
import com.yourname.healthtracker.presentation.viewmodel.ProfileViewModel
import com.yourname.healthtracker.di.AppModule
import com.yourname.healthtracker.presentation.screen.MainScreen
import com.yourname.healthtracker.presentation.screen.SettingsScreen
import com.yourname.healthtracker.presentation.screen.StatsScreen
import com.yourname.healthtracker.presentation.components.BottomNavigationBar
import com.yourname.healthtracker.presentation.components.Screen
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
                val foodVM: FoodViewModel = viewModel()
                val profileVM: ProfileViewModel = viewModel()
                val mainDB: AppDatabase = AppModule.provideDatabase(this)
                val daysDao: DaysDao = AppModule.providesDaysDao(mainDB)
                val userProfileDao: UserProfileDao = AppModule.providesProfileDao(mainDB)
                val repository: MainRepository = AppModule.provideMainRepository()
                val daysRepository: DaysRepository = AppModule.provideDaysRepository(daysDao)
                val profileRepository: ProfileRepository = AppModule.provideProfileRepository(userProfileDao)

                foodVM.loadDay(repository.getCurrentDate())
                LaunchedEffect(Unit) {
                    profileVM.loadProfile()
                }

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
                            MainScreen(foodVM, repository, profileVM)
                        }
                        composable(route = Screen.Stats.route) {
                            StatsScreen(foodVM, repository)
                        }
                        composable(route = Screen.Settings.route) {
                            SettingsScreen(foodVM, profileVM    )
                        }
                    }
                }
            }
        }
    }
}