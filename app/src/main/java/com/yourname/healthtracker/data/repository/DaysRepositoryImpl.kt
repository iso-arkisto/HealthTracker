package com.yourname.healthtracker.data.repository

import com.yourname.healthtracker.data.room.dao.DaysDao
import com.yourname.healthtracker.data.room.entities.FoodDay
import kotlinx.coroutines.flow.Flow

class DaysRepositoryImpl(
    private val dao: DaysDao
): DaysRepository {
    override suspend fun insertItem(item: FoodDay) {
        dao.insertItem(item)
    }

    override suspend fun deleteItem(item: FoodDay) {
        dao.deleteItem(item)
    }

    override fun getAllItems(): Flow<List<FoodDay>> {
        return dao.getAllItems()
    }

    override suspend fun findItemById(id: Int): FoodDay? {
        return dao.getItemById(id)
    }

    override suspend fun findItemByDate(date: String): FoodDay? {
        return dao.getItemByDate(date)
    }

    override suspend fun getOrCreateDay(date: String): FoodDay {
        val existingDay = dao.getItemByDate(date)

        return if(existingDay!=null) {
            existingDay
        } else {
            val newDay = FoodDay(date = date)
            dao.insertItem(newDay)
            newDay
        }
    }

}