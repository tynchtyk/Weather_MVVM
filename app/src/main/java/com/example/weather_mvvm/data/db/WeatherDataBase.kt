package com.example.weather_mvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather_mvvm.data.db.entity.CurrentWeatherEntry
import com.example.weather_mvvm.data.db.entity.WeatherLocation

@Database(
    entities = [CurrentWeatherEntry::class, WeatherLocation::class],
    version = 3,
    exportSchema = false
)
abstract class WeatherDataBase: RoomDatabase() {
    abstract fun currentWeatherDao() : CurrentWeatherDao
    abstract fun weatherLocationDao() : WeatherLocationDao

    companion object {
        @Volatile
        private var instance: WeatherDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WeatherDataBase::class.java,
                "forecast.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}