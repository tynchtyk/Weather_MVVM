package com.example.weather_mvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather_mvvm.data.provider.UnitProvider
import com.example.weather_mvvm.data.repository.WeatherRepository
import com.example.weather_mvvm.ui.weather.future.list.FutureListWeatherViewModel
import org.threeten.bp.LocalDate

class FutureDetailViewModelFactory(
    private val detailDate : LocalDate,
    private val weatherRepository : WeatherRepository,
    private val unitProvider: UnitProvider
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureDetailViewModel(detailDate, weatherRepository, unitProvider) as T
    }
}