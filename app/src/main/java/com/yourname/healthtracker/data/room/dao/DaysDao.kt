package com.yourname.healthtracker.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yourname.healthtracker.data.room.entities.FoodDay
import kotlinx.coroutines.flow.Flow

@Dao
interface DaysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(nameEntity: FoodDay)
    @Delete
    suspend fun deleteItem(nameEntity: FoodDay)
    @Query("SELECT * FROM days_table")
    fun getAllItems(): Flow<List<FoodDay>>

    @Query("SELECT * FROM days_table WHERE date == :itemDate LIMIT 1")
    suspend fun getItemByDate(itemDate: String): FoodDay?

    @Query("SELECT * FROM days_table WHERE id = :id LIMIT 1")
    suspend fun getItemById(id: Int): FoodDay?

}