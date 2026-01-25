package com.yourname.healthtracker.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yourname.healthtracker.data.room.entities.FoodDay
import com.yourname.healthtracker.data.room.entities.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProfile(profile: UserProfile)

    @Query("SELECT * FROM profile_table WHERE id = 0")
    suspend fun getUserProfile(): UserProfile?

    @Delete
    suspend fun deleteProfile(profile: UserProfile)

}