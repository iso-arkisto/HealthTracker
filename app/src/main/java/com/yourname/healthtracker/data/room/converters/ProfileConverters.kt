package com.yourname.healthtracker.data.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    @TypeConverter
    fun fromMutableList(value: MutableList<Int>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toMutableList(value: String): MutableList<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
