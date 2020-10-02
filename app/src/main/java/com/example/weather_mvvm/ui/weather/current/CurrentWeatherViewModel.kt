package com.example.weather_mvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.weather_mvvm.data.provider.UnitProvider
import com.example.weather_mvvm.data.repository.WeatherRepository
import com.example.weather_mvvm.internals.lazyDeferred

class CurrentWeatherViewModel(
    private val weatherRepository : WeatherRepository,
    unitProvider : UnitProvider
) : ViewModel() {
    val isMetric : Boolean
            get() = true
   val weather by lazyDeferred {
       weatherRepository.getCurrentWeather(isMetric)
   }

    val weatherLocation by lazyDeferred {
        weatherRepository.getWeatherLocation()
    }

}