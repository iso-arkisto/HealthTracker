package com.yourname.healthtracker.data

interface MainRepository {
    fun getCurrentDate(): String
    fun findFoodById(id: Int): Food?
}