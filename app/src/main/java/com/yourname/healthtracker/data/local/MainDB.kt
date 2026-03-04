package com.yourname.healthtracker.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yourname.healthtracker.data.local.converters.FoodLogConverter
import com.yourname.healthtracker.data.local.converters.ProfileConverters
import com.yourname.healthtracker.data.local.dao.DaysDao
import com.yourname.healthtracker.data.local.dao.UserProfileDao
import com.yourname.healthtracker.data.local.entities.FoodDay
import com.yourname.healthtracker.data.local.entities.UserProfile


@Database(
    entities = [FoodDay::class, UserProfile::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(FoodLogConverter::class, ProfileConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun DaysDao(): DaysDao
    abstract fun UserProfileDao(): UserProfileDao
}