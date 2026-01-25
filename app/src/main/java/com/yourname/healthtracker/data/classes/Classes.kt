package com.yourname.healthtracker.data.classes

import com.yourname.healthtracker.R

data class FoodLog(
    val amount: Int,
    val foodId: Int,
    val timestamp: Long = System.currentTimeMillis(),
)

data class Food(
    val id: Int,
    val name: Int,
    val type: FoodType,
    val calories: Double,
    val protein: Double,
    val fat: Double,
    val carbs: Double,
    val isCustom: Boolean = false,
)

enum class FoodType {
    FOOD, DRINK
}

enum class ActivityLevel(val multiplier: Double, val displayName: Int, val desc: Int) {
    SEDENTARY(1.2,R.string.sedentary,R.string.sed_desc),
    LIGHT(1.375,R.string.light,R.string.light_desc),
    MODERATE(1.55, R.string.moderate,R.string.moderate_desc),
    ACTIVE(1.725, R.string.active,R.string.active_desc),
    EXTREME(1.9, R.string.extreme,R.string.extreme_desc)
}

enum class FitnessGoal(val displayName: Int) {
    WEIGHT_LOSS(R.string.weight_loss),
    MAINTENANCE(R.string.weight_maintain),
    WEIGHT_GAIN(R.string.mass_gain)
}



