package com.example.weather_mvvm.data.db.unitlocalized.future.list

import com.example.weather_mvvm.ui.weather.future.list.FutureWeatherItem
import org.threeten.bp.LocalDate


interface UnitSpecificSimpleFutureWeatherEntry{
    val date: LocalDate
    val avgTemperature: Double
    val conditionText: String
    val conditionIconUrl: String
}