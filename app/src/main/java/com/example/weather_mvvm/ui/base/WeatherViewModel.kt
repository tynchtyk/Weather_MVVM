package com.example.weather_mvvm.ui.base

import androidx.lifecycle.ViewModel
import com.example.weather_mvvm.data.provider.UnitProvider
import com.example.weather_mvvm.data.repository.WeatherRepository
import com.example.weather_mvvm.internals.UnitSystem
import com.example.weather_mvvm.internals.lazyDeferred

abstract class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDeferred {
        weatherRepository.getWeatherLocation()
    }
}