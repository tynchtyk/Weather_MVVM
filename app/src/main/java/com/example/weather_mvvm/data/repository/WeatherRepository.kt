package com.example.weather_mvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.weather_mvvm.data.db.entity.WeatherLocation
import com.example.weather_mvvm.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry

interface WeatherRepository {
    suspend fun getCurrentWeather(metric: Boolean):LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation():LiveData<WeatherLocation>
}