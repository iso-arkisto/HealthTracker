package com.yourname.healthtracker.di

import android.content.Context
import androidx.room.Room
import com.yourname.healthtracker.data.repository.DaysRepository
import com.yourname.healthtracker.data.repository.DaysRepositoryImpl
import com.yourname.healthtracker.data.repository.MainRepository
import com.yourname.healthtracker.data.repository.MainRepositoryImpl
import com.yourname.healthtracker.data.repository.ProfileRepository
import com.yourname.healthtracker.data.repository.ProfileRepositoryImpl
import com.yourname.healthtracker.data.local.AppDatabase
import com.yourname.healthtracker.data.local.dao.DaysDao
import com.yourname.healthtracker.data.local.dao.UserProfileDao
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
    fun provideMainRepository(): MainRepository {
        return MainRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideDaysRepository(dao: DaysDao): DaysRepository {
        return DaysRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(dao: UserProfileDao): ProfileRepository {
        return ProfileRepositoryImpl(dao)
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