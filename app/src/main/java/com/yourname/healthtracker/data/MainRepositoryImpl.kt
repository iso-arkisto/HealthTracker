package com.yourname.healthtracker.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(): MainRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getCurrentDate(): String {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val currentDate = LocalDate.now().format(formatter)
        return currentDate
    }
}
