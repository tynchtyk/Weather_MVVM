package com.example.weather_mvvm.data.network

import androidx.lifecycle.LiveData
import com.example.weather_mvvm.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather : LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location : String
    )
}