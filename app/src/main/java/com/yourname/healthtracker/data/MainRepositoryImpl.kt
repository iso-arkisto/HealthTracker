package com.yourname.healthtracker.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(): MainRepository {
    private val foodList = listOf<Food>(
        Food(1,"water","drink",0.0,0.0,0.0,0.0),
        Food(2,"apple","food",52.0,0.25,13.8,0.15),
        Food(3, "banana", "food", 89.0,1.1,22.8,0.3),
        Food(4, "raw potato", "food", 77.0, 2.0, 17.5, 0.1),
        Food(5, "raw carrot", "food", 41.0, 0.9, 9.6, 0.2),
        Food(6, "tangerine", "food", 53.0, 0.8, 13.3, 0.3),

    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getCurrentDate(): String {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val currentDate = LocalDate.now().format(formatter)
        return currentDate
    }

    override fun findFoodById(id: Int): Food? {
        return foodList.find { food -> food.id == id }
    }
}
