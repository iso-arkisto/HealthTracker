package com.yourname.healthtracker.data.room.converters

import androidx.room.TypeConverter
import com.yourname.healthtracker.data.classes.ActivityLevel
import com.yourname.healthtracker.data.classes.FitnessGoal

class ProfileConverters {
    @TypeConverter
    fun fromActivityLevel(value: ActivityLevel) = value.name

    @TypeConverter
    fun toActivityLevel(value: String) = ActivityLevel.valueOf(value)

    @TypeConverter
    fun fromFitnessGoal(value: FitnessGoal) = value.name

    @TypeConverter
    fun toFitnessGoal(value: String) = FitnessGoal.valueOf(value)
}
