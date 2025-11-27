package com.yourname.healthtracker.data

import com.yourname.healthtracker.R

data class WaterDay(
    val date: String,
    var goal: Int,
    var amount: Int
)

data class FoodDay(
    val date: String,

    var waterGoal: Int = 2500,
    var caloriesGoal: Int = 2200,
    var waterAmount: Int = 0,

    var logs: List<FoodLog> = emptyList<FoodLog>(),

    var calories: Double = 0.0,
    var protein: Double = 0.0,
    var carbs: Double = 0.0,
    var fat: Double = 0.0,
)

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

data class UserProfile(
    var weight: Double = 60.0,
    var height: Double = 170.0,
    var age: Int = 25,
    var gender: Int = R.string.male,
    var activityLevel: ActivityLevel = ActivityLevel.MODERATE,
    var fitnessGoal: FitnessGoal = FitnessGoal.MAINTENANCE
)

class CalorieCalculator {

    companion object {
        fun calculateDailyCalories(profile: UserProfile): Int {
            val bmr = calculateBMR(profile)

            val tdee = bmr * profile.activityLevel.multiplier

            return adjustForGoal(tdee, profile.fitnessGoal).toInt()
        }

        private fun calculateBMR(profile: UserProfile): Double {
            return if (profile.gender == R.string.male) {
                10 * profile.weight + 6.25 * profile.height - 5 * profile.age + 5
            } else {
                10 * profile.weight + 6.25 * profile.height - 5 * profile.age - 161
            }
        }

        private fun adjustForGoal(tdee: Double, goal: FitnessGoal): Double {
            return when (goal) {
                FitnessGoal.WEIGHT_LOSS -> tdee * 0.85 - 500
                FitnessGoal.MAINTENANCE -> tdee
                FitnessGoal.WEIGHT_GAIN -> tdee * 1.15 + 300
            }
        }

        fun calculateMacros(calories: Int, goal: FitnessGoal): MacroNutrients {
            val proteinPercentage = when (goal) {
                FitnessGoal.WEIGHT_LOSS -> 0.35
                FitnessGoal.MAINTENANCE -> 0.30
                FitnessGoal.WEIGHT_GAIN -> 0.25
            }

            val fatPercentage = 0.25    // 25% для всех целей
            val carbPercentage = 1 - proteinPercentage - fatPercentage

            return MacroNutrients(
                protein = (calories * proteinPercentage / 4).toInt(),     // 1г белка = 4 ккал
                fats = (calories * fatPercentage / 9).toInt(),           // 1г жира = 9 ккал
                carbs = (calories * carbPercentage / 4).toInt()          // 1г углеводов = 4 ккал
            )
        }
    }
}

data class MacroNutrients(
    val protein: Int,
    val fats: Int,
    val carbs: Int
)