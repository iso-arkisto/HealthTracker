package com.yourname.healthtracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "days_table")
data class FoodDay(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val date: String,

    var waterGoal: Int = 2500,
    var caloriesGoal: Int = 2200,
    var proteinGoal: Double = 84.0,
    var fatsGoal: Double = 60.0,
    var carbsGoal: Double = 300.0,
    var waterAmount: Int = 0,

    var logs: List<FoodLog> = emptyList<FoodLog>(),

    var calories: Double = 0.0,
    var protein: Double = 0.0,
    var carbs: Double = 0.0,
    var fat: Double = 0.0,
)