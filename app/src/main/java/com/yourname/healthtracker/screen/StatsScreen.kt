package com.yourname.healthtracker.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourname.healthtracker.data.FoodViewModel
import com.yourname.healthtracker.data.MainRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StatsScreen(foodViewModel: FoodViewModel, repository: MainRepository) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(foodViewModel.day.logs) {
                log ->
                Text(
                    text = "+${log.amount} ml of ${repository.findFoodById(log.foodId)?.name} (${SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                        Date(log.timestamp)
                    )})"
                )
            }
        }
    }
}