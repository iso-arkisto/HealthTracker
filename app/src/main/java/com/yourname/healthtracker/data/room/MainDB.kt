package com.yourname.healthtracker.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yourname.healthtracker.data.room.converters.FoodLogConverter
import com.yourname.healthtracker.data.room.converters.ProfileConverters
import com.yourname.healthtracker.data.room.dao.DaysDao
import com.yourname.healthtracker.data.room.dao.UserProfileDao
import com.yourname.healthtracker.data.room.entities.FoodDay
import com.yourname.healthtracker.data.room.entities.UserProfile


@Database(entities = [FoodDay::class, UserProfile::class], version = 2)
@TypeConverters(FoodLogConverter::class, ProfileConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun DaysDao(): DaysDao
    abstract fun UserProfileDao(): UserProfileDao
}