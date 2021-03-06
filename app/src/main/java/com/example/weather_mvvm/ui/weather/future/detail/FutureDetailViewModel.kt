package com.example.weather_mvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import com.example.weather_mvvm.data.provider.UnitProvider
import com.example.weather_mvvm.data.repository.WeatherRepository
import com.example.weather_mvvm.internals.lazyDeferred
import com.example.weather_mvvm.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureDetailViewModel(
    private val detailDate : LocalDate,
    private val weatherRepository : WeatherRepository,
    unitProvider : UnitProvider
) : WeatherViewModel(weatherRepository, unitProvider) {
    val weather by lazyDeferred {
        weatherRepository.getFutureWeatherDetail(detailDate, super.isMetricUnit)
    }
}