package com.example.weather_mvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.weather_mvvm.data.repository.WeatherRepository
import com.example.weather_mvvm.internals.lazyDeferred

class CurrentWeatherViewModel(
    private val weatherRepository : WeatherRepository
) : ViewModel() {
   val weather by lazyDeferred {
       weatherRepository.getCurrentWeather()
   }

    val weatherLocation by lazyDeferred {
        weatherRepository.getWeatherLocation()
    }

}