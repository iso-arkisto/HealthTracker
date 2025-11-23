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

    fun addFood(name: String, amount: Int, timestamp: Long = System.currentTimeMillis()) {
        val current = day
        day = current.copy(
            waterAmount = current.waterAmount + amount,
            logs = current.logs + FoodLog(
                amount = amount,
                name = name,
                timestamp = timestamp
            )
        )
        waterProgress = (day.waterAmount.toFloat()/day.waterGoal)
    }

}