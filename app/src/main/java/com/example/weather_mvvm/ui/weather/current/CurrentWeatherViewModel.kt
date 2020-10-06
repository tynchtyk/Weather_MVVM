package com.example.weather_mvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.weather_mvvm.data.provider.UnitProvider
import com.example.weather_mvvm.data.repository.WeatherRepository
import com.example.weather_mvvm.internals.UnitSystem
import com.example.weather_mvvm.internals.lazyDeferred

class CurrentWeatherViewModel(
    private val weatherRepository : WeatherRepository,
    unitProvider : UnitProvider
) : ViewModel() {
   private val unitSystem = unitProvider.getUnitSystem()
    val isMetric : Boolean
        get() = unitSystem == UnitSystem.METRIC
   val weather by lazyDeferred {
       weatherRepository.getCurrentWeather(isMetric)
   }

    val weatherLocation by lazyDeferred {
        weatherRepository.getWeatherLocation()
    }

}