package com.yourname.healthtracker.di

import android.content.Context
import androidx.room.Room
import com.yourname.healthtracker.data.repository.MainRepository
import com.yourname.healthtracker.data.repository.MainRepositoryImpl
import com.yourname.healthtracker.data.room.AppDatabase
import com.yourname.healthtracker.data.room.dao.DaysDao
import com.yourname.healthtracker.data.room.dao.UserProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMainRepository(daysDao: DaysDao, profileDao: UserProfileDao): MainRepository {
        return MainRepositoryImpl(daysDao, profileDao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "health_tracker_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesDaysDao(database: AppDatabase): DaysDao {
        return database.DaysDao()
    }

    @Provides
    fun providesProfileDao(database: AppDatabase): UserProfileDao {
        return database.UserProfileDao()
    }
}