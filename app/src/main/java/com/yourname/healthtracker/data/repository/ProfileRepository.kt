package com.yourname.healthtracker.data.repository

import com.yourname.healthtracker.data.room.entities.FoodDay
import com.yourname.healthtracker.data.room.entities.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun insertItem(item: UserProfile)
    suspend fun deleteItem(item: UserProfile)
    fun getAllItems(): Flow<List<UserProfile>>
    suspend fun findItemById(id: Int): UserProfile?
    suspend fun getOrCreateProfile(id: Int): UserProfile

}