package com.yourname.healthtracker.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {
    var day = FoodDay(
        date = repository.getCurrentDate(),
    )
    var waterProgress: Float by mutableFloatStateOf(0f)
    var caloriesProgress: Float by mutableFloatStateOf(0f)

    fun addFood(id: Int, amount: Int, timestamp: Long = System.currentTimeMillis()) {
        val current = day
        val food = repository.findFoodById(id)

        if(food == null) { return }

        day = current.copy(
            waterAmount = current.waterAmount + amount,
            logs = current.logs + FoodLog(
                amount = amount,
                foodId = id,
                timestamp = timestamp
            ),
            calories = current.calories+(food.calories*(amount/100)),
            protein = current.protein+(food.protein*(amount/100)),
            fat = current.fat+(food.fat*(amount/100)),
            carbs = current.carbs+(food.carbs*(amount/100))
        )

        waterProgress = (day.waterAmount.toFloat()/day.waterGoal)
        caloriesProgress = (day.calories.toFloat()/day.caloriesGoal)

    }

    fun updateSettings(waterGoal: Int) {
        val current = day
        day = current.copy(
            waterGoal = waterGoal
        )

        waterProgress = (day.waterAmount.toFloat()/day.waterGoal)
        caloriesProgress = (day.calories.toFloat()/day.caloriesGoal)

    }

}