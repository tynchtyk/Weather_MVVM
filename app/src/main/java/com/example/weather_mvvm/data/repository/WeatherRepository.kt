package com.example.weather_mvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.weather_mvvm.data.db.entity.WeatherLocation
import com.example.weather_mvvm.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.example.weather_mvvm.data.db.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.example.weather_mvvm.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

interface WeatherRepository {
    suspend fun getCurrentWeather(metric: Boolean):LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getFutureWeatherList(startDate : LocalDate, metric: Boolean):LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>
    suspend fun getFutureWeatherDetail(startDate : LocalDate, metric: Boolean):LiveData<out UnitSpecificDetailFutureWeatherEntry>
    suspend fun getWeatherLocation():LiveData<WeatherLocation>
}