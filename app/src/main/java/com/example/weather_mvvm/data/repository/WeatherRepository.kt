package com.example.weather_mvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.weather_mvvm.data.db.entity.CurrentWeatherEntry
import com.example.weather_mvvm.data.db.entity.WeatherLocation

interface WeatherRepository {
    suspend fun getCurrentWeather():LiveData<CurrentWeatherEntry>
    suspend fun getWeatherLocation():LiveData<WeatherLocation>
}