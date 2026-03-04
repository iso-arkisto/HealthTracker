package com.yourname.healthtracker.data.repository

import com.yourname.healthtracker.domain.Food
import com.yourname.healthtracker.domain.FoodType

interface MainRepository {
    fun getCurrentDate(): String
    fun findFoodById(id: Int): Food?
    fun getAllFood(type: FoodType): List<Food>
}