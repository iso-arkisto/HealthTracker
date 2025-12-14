package com.yourname.healthtracker

import android.app.Application
import com.yourname.healthtracker.data.MainDb
import dagger.hilt.android.HiltAndroidApp
import kotlin.getValue

@HiltAndroidApp
class App: Application() {
//    val db by lazy { MainDb.createDataBase(this) }
}