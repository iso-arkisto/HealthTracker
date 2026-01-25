package com.yourname.healthtracker.data.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.healthtracker.data.classes.FoodLog
import com.yourname.healthtracker.data.classes.FoodType
import com.yourname.healthtracker.data.repository.MainRepository
import com.yourname.healthtracker.data.room.AppDatabase
import com.yourname.healthtracker.data.room.dao.DaysDao
import com.yourname.healthtracker.data.room.entities.FoodDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val repository: MainRepository,
    private val daysDao: DaysDao
): ViewModel() {
    private val _currentDay = MutableStateFlow(FoodDay(date = repository.getCurrentDate()))

    val currentDay = _currentDay.asStateFlow()

    fun loadDay(date: String) {
        viewModelScope.launch {
            val day = repository.getOrCreateDay(date)
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

            if(food.type == FoodType.FOOD) {
                _currentDay.value = current.copy(
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
            } else {
                _currentDay.value = current.copy(
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
            }

            waterProgress = (currentDay.value.waterAmount.toFloat()/currentDay.value.waterGoal)
            caloriesProgress = (currentDay.value.calories.toFloat()/currentDay.value.caloriesGoal)
            proteinProgress = (currentDay.value.protein.toFloat()/currentDay.value.proteinGoal.toFloat())
            fatsProgress = (currentDay.value.fat.toFloat()/currentDay.value.fatsGoal.toFloat())
            carbsProgress = (currentDay.value.carbs.toFloat()/currentDay.value.carbsGoal.toFloat())

            viewModelScope.launch {
                daysDao.insertItem(currentDay.value)
            }

        }
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

        waterProgress = (currentDay.value.waterAmount.toFloat()/currentDay.value.waterGoal)
        caloriesProgress = (currentDay.value.calories.toFloat()/currentDay.value.caloriesGoal)
        proteinProgress = (currentDay.value.protein.toFloat()/currentDay.value.proteinGoal.toFloat())
        fatsProgress = (currentDay.value.fat.toFloat()/currentDay.value.fatsGoal.toFloat())
        carbsProgress = (currentDay.value.carbs.toFloat()/currentDay.value.carbsGoal.toFloat())

        viewModelScope.launch {
            daysDao.insertItem(currentDay.value)
        }
    }

}