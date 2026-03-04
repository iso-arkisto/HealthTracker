package com.yourname.healthtracker.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.healthtracker.domain.FoodLog
import com.yourname.healthtracker.domain.FoodType
import com.yourname.healthtracker.data.repository.DaysRepository
import com.yourname.healthtracker.data.repository.MainRepository
import com.yourname.healthtracker.data.local.entities.FoodDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.plus

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val repository: MainRepository,
    private val daysRepository: DaysRepository
): ViewModel() {
    private val _currentDay = MutableStateFlow(FoodDay(date = repository.getCurrentDate()))

    val currentDay = _currentDay.asStateFlow()

    fun updateDay() {
        waterProgress = (currentDay.value.waterAmount.toFloat()/currentDay.value.waterGoal)
        caloriesProgress = (currentDay.value.calories.toFloat()/currentDay.value.caloriesGoal)
        proteinProgress = (currentDay.value.protein.toFloat()/currentDay.value.proteinGoal.toFloat())
        fatsProgress = (currentDay.value.fat.toFloat()/currentDay.value.fatsGoal.toFloat())
        carbsProgress = (currentDay.value.carbs.toFloat()/currentDay.value.carbsGoal.toFloat())

        viewModelScope.launch {
            daysRepository.insertItem(currentDay.value)
        }
    }

     fun removeFoodLog(id: String) {
        val foodLog: FoodLog? = currentDay.value.logs.find { it.id == id }

        if(foodLog!=null) {
            val current = currentDay.value
            val food = repository.findFoodById(foodLog.foodId) ?: return

            _currentDay.value = current.copy(
                waterAmount = if(food.type == FoodType.DRINK) {
                    current.waterAmount-foodLog.amount
                } else {
                    current.waterAmount
                },
                calories = current.calories-(food.calories*(foodLog.amount/100)),
                protein = current.protein-(food.protein*(foodLog.amount/100)),
                fat = current.fat-(food.fat*(foodLog.amount/100)),
                carbs = current.carbs-(food.carbs*(foodLog.amount/100)),
                logs = current.logs - foodLog
            )

            updateDay()
        }
    }



    fun loadDay(date: String) {
        viewModelScope.launch {
            val day = daysRepository.getOrCreateDay(date)
            _currentDay.value = day

            waterProgress = (currentDay.value.waterAmount.toFloat()/currentDay.value.waterGoal)
            caloriesProgress = (currentDay.value.calories.toFloat()/currentDay.value.caloriesGoal)
            proteinProgress = (currentDay.value.protein.toFloat()/currentDay.value.proteinGoal.toFloat())
            fatsProgress = (currentDay.value.fat.toFloat()/currentDay.value.fatsGoal.toFloat())
            carbsProgress = (currentDay.value.carbs.toFloat()/currentDay.value.carbsGoal.toFloat())
        }
    }


    var waterProgress: Float by mutableFloatStateOf(0f)
    var caloriesProgress: Float by mutableFloatStateOf(0f)
    var proteinProgress: Float by mutableFloatStateOf(0f)
    var fatsProgress: Float by mutableFloatStateOf(0f)
    var carbsProgress: Float by mutableFloatStateOf(0f)
    var waterAddValue: Int by mutableIntStateOf(230)
    var foodAddValue: Int by mutableIntStateOf(100)
    var drinkAddValue: Int by mutableIntStateOf(230)




    fun addFood(id: Int, amount: Int, timestamp: Long = System.currentTimeMillis()) {
        if(currentDay.value!=null) {
            val current = currentDay.value
            val food = repository.findFoodById(id) ?: return

            _currentDay.value = current.copy(
                waterAmount = if(food.type == FoodType.DRINK) {
                    current.waterAmount+amount
                } else {
                    current.waterAmount
                },
                calories = current.calories+(food.calories*(amount/100)),
                protein = current.protein+(food.protein*(amount/100)),
                fat = current.fat+(food.fat*(amount/100)),
                carbs = current.carbs+(food.carbs*(amount/100)),
                logs = current.logs + FoodLog(
                    foodId = id,
                    amount = amount,
                    timestamp = timestamp
                )
            )

            updateDay()

        }
    }

    suspend fun insertDay(item: FoodDay) {
        daysRepository.insertItem(item)
    }

    fun updateSettings(
        waterGoal: Int,
        caloriesGoal: Int,
        proteinGoal: Double,
        fatsGoal: Double,
        carbsGoal: Double,
    ) {
        val current = currentDay.value
        _currentDay.value = current.copy(
            waterGoal = waterGoal,
            caloriesGoal = caloriesGoal,
            proteinGoal = proteinGoal,
            fatsGoal = fatsGoal,
            carbsGoal = carbsGoal
        )

        updateDay()
    }

}