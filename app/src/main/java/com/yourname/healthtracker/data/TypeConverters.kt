package com.yourname.healthtracker.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromFoodLogList(value: List<FoodLog>?): String? {
        if (value == null) {
            return null
        }
        val typeToken = object : TypeToken<List<FoodLog>>() {}.type
        return Gson().toJson(value, typeToken)
    }

    @TypeConverter
    fun toFoodLogList(value: String?): List<FoodLog>? {
        if (value == null) {
            return null
        }
        val typeToken = object : TypeToken<List<FoodLog>>() {}.type
        return Gson().fromJson(value, typeToken)
    }
}