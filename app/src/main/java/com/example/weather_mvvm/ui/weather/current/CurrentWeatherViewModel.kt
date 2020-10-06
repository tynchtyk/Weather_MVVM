package com.example.weather_mvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.weather_mvvm.data.provider.UnitProvider
import com.example.weather_mvvm.data.repository.WeatherRepository
import com.example.weather_mvvm.internals.UnitSystem
import com.example.weather_mvvm.internals.lazyDeferred
import com.example.weather_mvvm.ui.base.WeatherViewModel

class CurrentWeatherViewModel(
    private val weatherRepository : WeatherRepository,
    unitProvider : UnitProvider
) : WeatherViewModel(weatherRepository, unitProvider) {

   val weather by lazyDeferred {
       weatherRepository.getCurrentWeather(super.isMetricUnit)
   }

}