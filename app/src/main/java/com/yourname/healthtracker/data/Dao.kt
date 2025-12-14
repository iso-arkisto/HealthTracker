package com.yourname.healthtracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(nameEntity: FoodDay)
    @Delete
    suspend fun deleteItem(nameEntity: FoodDay)
    @Query("SELECT * FROM days_table")
    fun getAllItems(): Flow<List<FoodDay>>

}