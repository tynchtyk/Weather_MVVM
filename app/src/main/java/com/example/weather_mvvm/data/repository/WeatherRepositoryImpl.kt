package com.example.weather_mvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.weather_mvvm.data.db.CurrentWeatherDao
import com.example.weather_mvvm.data.db.entity.CurrentWeatherEntry
import com.example.weather_mvvm.data.network.response.CurrentWeatherResponse
import com.example.weather_mvvm.data.network.WeatherNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class WeatherRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetWorkDataSource : WeatherNetworkDataSource
) : WeatherRepository {

    init{
        weatherNetWorkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrenWeather(newCurrentWeather)
        }
    }
    override suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry> {
        initWeatherData()
        return  withContext(Dispatchers.IO){
            return@withContext currentWeatherDao.getWeatherEntry()
        }
    }

    private fun persistFetchedCurrenWeather(fetchedWeather : CurrentWeatherResponse){
        GlobalScope.launch ( Dispatchers.IO ){
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
        }
    }

    private suspend fun initWeatherData(){
        if(isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetWorkDataSource.fetchCurrentWeather("Seoul")
    }

    private fun isFetchCurrentNeeded(lastFetchTime : ZonedDateTime): Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}