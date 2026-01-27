package com.yourname.healthtracker.data.repository

import com.yourname.healthtracker.data.room.dao.DaysDao
import com.yourname.healthtracker.data.room.dao.UserProfileDao
import com.yourname.healthtracker.data.room.entities.FoodDay
import com.yourname.healthtracker.data.room.entities.UserProfile
import kotlinx.coroutines.flow.Flow

class ProfileRepositoryImpl(
    private val dao: UserProfileDao
): ProfileRepository {
    override suspend fun insertItem(item: UserProfile) {
        dao.insertItem(item)
    }

    override suspend fun deleteItem(item: UserProfile) {
        dao.deleteItem(item)
    }

    override fun getAllItems(): Flow<List<UserProfile>> {
        return dao.getAllItems()
    }

    override suspend fun findItemById(id: Int): UserProfile? {
        return dao.getItemById(id)
    }


    override suspend fun getOrCreateProfile(id: Int): UserProfile {
        val existingProfile = dao.getItemById(id)

        return if(existingProfile!=null) {
            existingProfile
        } else {
            val newProfile = UserProfile()
            dao.insertItem(newProfile)
            newProfile
        }

    }
}