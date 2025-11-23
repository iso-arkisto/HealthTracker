package com.yourname.healthtracker.di

import com.yourname.healthtracker.data.MainRepository
import com.yourname.healthtracker.data.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module

object AppModule {
    @Provides
    @Singleton

    fun provideMainRepository(): MainRepository {
        return MainRepositoryImpl()
    }
}