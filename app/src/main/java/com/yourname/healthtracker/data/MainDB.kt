package com.yourname.healthtracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class) // Это гарантирует, что БД будет синглтоном на протяжении жизни приложения
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MainDb {
        return Room.databaseBuilder(
            appContext,
            MainDb::class.java,
            "healthtracker.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database: MainDb): Dao {
        return database.dao
    }
}

@Database(
    entities = [
        FoodDay::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MainDb: RoomDatabase() {

    abstract val dao: Dao
//    companion object{
//        fun createDataBase(context: Context): MainDb{
//            return Room.databaseBuilder(
//                context,
//                MainDb::class.java,
//                "healthtracker.db"
//            ).build()
//        }
//    }
}