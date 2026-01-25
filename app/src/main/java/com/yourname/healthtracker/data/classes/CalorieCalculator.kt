package com.yourname.healthtracker.data.classes

import com.yourname.healthtracker.R
import com.yourname.healthtracker.data.room.entities.UserProfile

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

            val fatPercentage = 0.25
            val carbPercentage = 1 - proteinPercentage - fatPercentage

            return MacroNutrients(
                protein = (calories * proteinPercentage / 4).toInt(),
                fats = (calories * fatPercentage / 9).toInt(),
                carbs = (calories * carbPercentage / 4).toInt()
            )
        }
    }
}

data class MacroNutrients(
    val protein: Int,
    val fats: Int,
    val carbs: Int
)