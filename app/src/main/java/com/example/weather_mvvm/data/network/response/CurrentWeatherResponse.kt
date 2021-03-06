package com.example.weather_mvvm.data.network.response

import com.example.weather_mvvm.data.db.entity.CurrentWeatherEntry
import com.example.weather_mvvm.data.db.entity.WeatherLocation
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(

    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,

    val location: WeatherLocation
)