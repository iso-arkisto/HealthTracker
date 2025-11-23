package com.yourname.healthtracker.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WaterViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {
    var waterDay: WaterDay = WaterDay(
        date = repository.getCurrentDate(),
        goal = 1000,
        amount = 0
    )
    var waterProgress: Float by mutableFloatStateOf(0f)

    fun addWater(amountToAdd: Int) {
        val current = waterDay
        waterDay = current.copy(
            amount = current.amount + amountToAdd
        )
        waterProgress = (waterDay.amount.toFloat()/waterDay.goal)
    }

}