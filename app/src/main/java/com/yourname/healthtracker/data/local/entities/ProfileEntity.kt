package com.yourname.healthtracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yourname.healthtracker.R
import com.yourname.healthtracker.domain.ActivityLevel
import com.yourname.healthtracker.domain.FitnessGoal
import java.util.Collections.emptyList

@Entity(tableName = "profile_table")
data class UserProfile(
    @PrimaryKey val id: Int = 0,
    var weight: Double = 60.0,
    var height: Double = 170.0,
    var age: Int = 25,
    var gender: Int = R.string.male,
    var activityLevel: ActivityLevel = ActivityLevel.MODERATE,
    var fitnessGoal: FitnessGoal = FitnessGoal.MAINTENANCE,
    var recentProducts: MutableList<Int> = emptyList()
)