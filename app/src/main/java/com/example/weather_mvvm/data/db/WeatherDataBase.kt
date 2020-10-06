package com.example.weather_mvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weather_mvvm.data.db.entity.CurrentWeatherEntry
import com.example.weather_mvvm.data.db.entity.FutureWeatherEntry
import com.example.weather_mvvm.data.db.entity.WeatherLocation

@Database(
    entities = [CurrentWeatherEntry::class, FutureWeatherEntry::class, WeatherLocation::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(LocalDateConverter::class)
abstract class WeatherDataBase: RoomDatabase() {
    abstract fun currentWeatherDao() : CurrentWeatherDao
    abstract fun futureWeatherDao() : FutureWeatherDao
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