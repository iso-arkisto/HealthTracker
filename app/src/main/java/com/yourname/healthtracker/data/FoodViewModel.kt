package com.yourname.healthtracker.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val repository: MainRepository,
    val db: MainDb
): ViewModel() {
    private val _day = MutableStateFlow(FoodDay(
        date = repository.getCurrentDate()
    ))
    val day: StateFlow<FoodDay> = _day


    var waterProgress: Float by mutableFloatStateOf(0f)
    var caloriesProgress: Float by mutableFloatStateOf(0f)
    var proteinProgress: Float by mutableFloatStateOf(0f)
    var fatsProgress: Float by mutableFloatStateOf(0f)
    var carbsProgress: Float by mutableFloatStateOf(0f)
    var waterAddValue: Int by mutableIntStateOf(230)
    var foodAddValue: Int by mutableIntStateOf(100)
    var drinkAddValue: Int by mutableIntStateOf(230)


    fun addFood(id: Int, amount: Int, timestamp: Long = System.currentTimeMillis()) {
        val current = day.value
        val food = repository.findFoodById(id)

        if(food == null) { return }

        if(food.type == FoodType.FOOD) {
            _day.value = current.copy(
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
            _day.value = current.copy(
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

        waterProgress = (day.value.waterAmount.toFloat()/day.value.waterGoal)
        caloriesProgress = (day.value.calories.toFloat()/day.value.caloriesGoal)
        proteinProgress = (day.value.protein.toFloat()/day.value.proteinGoal.toFloat())
        fatsProgress = (day.value.fat.toFloat()/day.value.fatsGoal.toFloat())
        carbsProgress = (day.value.carbs.toFloat()/day.value.carbsGoal.toFloat())

        viewModelScope.launch {
            db.dao.insertItem(day.value)
        }

    }

    fun updateSettings(
        waterGoal: Int,
        caloriesGoal: Int,
        proteinGoal: Double,
        fatsGoal: Double,
        carbsGoal: Double,
    ) {
        val current = day.value
        _day.value = current.copy(
            waterGoal = waterGoal,
            caloriesGoal = caloriesGoal,
            proteinGoal = proteinGoal,
            fatsGoal = fatsGoal,
            carbsGoal = carbsGoal
        )

        waterProgress = (day.value.waterAmount.toFloat()/day.value.waterGoal)
        caloriesProgress = (day.value.calories.toFloat()/day.value.caloriesGoal)
        proteinProgress = (day.value.protein.toFloat()/day.value.proteinGoal.toFloat())
        fatsProgress = (day.value.fat.toFloat()/day.value.fatsGoal.toFloat())
        carbsProgress = (day.value.carbs.toFloat()/day.value.carbsGoal.toFloat())
    }

}