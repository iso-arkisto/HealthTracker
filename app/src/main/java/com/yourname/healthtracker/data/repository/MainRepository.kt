package com.yourname.healthtracker.data.repository

import com.yourname.healthtracker.data.classes.Food
import com.yourname.healthtracker.data.classes.FoodType
import com.yourname.healthtracker.data.room.entities.FoodDay
import com.yourname.healthtracker.data.room.entities.UserProfile

interface MainRepository {
    fun getCurrentDate(): String
    fun findFoodById(id: Int): Food?
    fun getAllFood(type: FoodType): List<Food>
    suspend fun getOrCreateDay(date: String): FoodDay
    suspend fun getOrCreateProfile(): UserProfile
}