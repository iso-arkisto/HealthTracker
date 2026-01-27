package com.yourname.healthtracker.data.repository

import com.yourname.healthtracker.data.room.entities.FoodDay
import kotlinx.coroutines.flow.Flow

interface DaysRepository {
    suspend fun insertItem(item: FoodDay)
    suspend fun deleteItem(item: FoodDay)

    suspend fun getOrCreateDay(date: String): FoodDay
    fun getAllItems(): Flow<List<FoodDay>>
    suspend fun findItemById(id: Int): FoodDay?
    suspend fun findItemByDate(date: String): FoodDay?
}