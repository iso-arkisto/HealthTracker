package com.yourname.healthtracker.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourname.healthtracker.R
import com.yourname.healthtracker.data.Food
import com.yourname.healthtracker.data.FoodType
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
            items(foodViewModel.day.logs.sortedByDescending { it.timestamp }) {
                log ->
                val food: Food? = repository.findFoodById(log.foodId)
                var name: String
                var type: FoodType

                if(food != null) {
                    name = stringResource(food.name)
                    type = food.type
                } else {
                    name = "???"
                    type = FoodType.DRINK
                }

                Text(
                    text = "+${log.amount} ${if(type== FoodType.FOOD) stringResource(R.string.grams) else stringResource(R.string.milliliters) } of $name (${SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                        Date(log.timestamp)
                    )})"
                )
            }
        }
    }
}