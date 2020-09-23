package com.example.weather_mvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.weather_mvvm.data.db.entity.CurrentWeatherEntry

interface WeatherRepository {
    suspend fun getCurrentWeather():LiveData<CurrentWeatherEntry>
}