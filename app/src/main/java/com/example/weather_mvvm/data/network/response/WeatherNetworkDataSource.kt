package com.example.weather_mvvm.data.network.response

import androidx.lifecycle.LiveData

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather : LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location : String
    )
}