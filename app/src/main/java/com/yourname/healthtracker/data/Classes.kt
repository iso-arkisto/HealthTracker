package com.yourname.healthtracker.data

data class WaterDay(
    val date: String,
    var goal: Int,
    var amount: Int
)

data class FoodDay(
    val date: String,

    var waterGoal: Int = 2500,
    var waterAmount: Int = 0,

    var logs: List<FoodLog> = emptyList<FoodLog>(),

    var calories: Double = 0.0,
    var protein: Double = 0.0,
    var carbs: Double = 0.0,
    var fat: Double = 0.0,
)

data class FoodLog(
    val amount: Int,
    val name: String,
    val timestamp: Long = System.currentTimeMillis(),
)

data class Food(
    val id: Int,
    val name: String,
    val type: String,
    val calories: Double,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val isCustom: Boolean = false,
)