package com.example.weather_mvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.weather_mvvm.data.db.CurrentWeatherDao
import com.example.weather_mvvm.data.db.FutureWeatherDao
import com.example.weather_mvvm.data.db.WeatherLocationDao
import com.example.weather_mvvm.data.db.entity.WeatherLocation
import com.example.weather_mvvm.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.example.weather_mvvm.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.example.weather_mvvm.data.network.FORECAST_DAYS_COUNT
import com.example.weather_mvvm.data.network.response.CurrentWeatherResponse
import com.example.weather_mvvm.data.network.WeatherNetworkDataSource
import com.example.weather_mvvm.data.network.response.FutureWeatherResponse
import com.example.weather_mvvm.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import java.util.*

class WeatherRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val futureWeatherDao : FutureWeatherDao,
    private val weatherLocationDao : WeatherLocationDao,
    private val weatherNetWorkDataSource : WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : WeatherRepository {

    init{
        weatherNetWorkDataSource.apply {
            downloadedCurrentWeather.observeForever { newCurrentWeather ->
                persistFetchedCurrenWeather(newCurrentWeather)
            }

            downloadedFutureWeather.observeForever { newFutureWeather ->
                persistFetchedFutureWeather(newFutureWeather)
            }
        }
    }
    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        initWeatherData()
        return  withContext(Dispatchers.IO){
            return@withContext if (metric) currentWeatherDao.getWeatherMetric()
                                else currentWeatherDao.getWeatherImperial()
        }
    }

    override suspend fun getFutureWeatherList(
        startDate: LocalDate,
        metric: Boolean
    ): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>> {
        initWeatherData()
        return  withContext(Dispatchers.IO){
            return@withContext if (metric) futureWeatherDao.getSimpleWeatherForecastMetric(startDate)
            else futureWeatherDao.getSimpleWeatherForecastImperial(startDate)
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return  withContext(Dispatchers.IO){
            return@withContext weatherLocationDao.getLocation()
        }
    }

    private fun persistFetchedCurrenWeather(fetchedWeather : CurrentWeatherResponse){
        GlobalScope.launch ( Dispatchers.IO ){
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private fun persistFetchedFutureWeather(fetchedWeather : FutureWeatherResponse){
        fun deleteOldForecastData() {
            val today = LocalDate.now()
            futureWeatherDao.deleteOldEntries(today)
        }

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldForecastData()
            val futureWeatherList = fetchedWeather.futureWeatherEntries.entries
            futureWeatherDao.insert(futureWeatherList)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }


    private suspend fun initWeatherData(){
        val lastWeatherLocation = weatherLocationDao.getLocationNonLive()
        if(lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation )){
            fetchCurrentWeather()
            fetchFutureWeather()
            return
        }

        if(isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()

        if(isFetchFutureNeeded())
            fetchFutureWeather()

    }

    private suspend fun fetchCurrentWeather() {
        weatherNetWorkDataSource.fetchCurrentWeather(locationProvider.getPreferredLocationString())
    }


    private suspend fun fetchFutureWeather() {
        weatherNetWorkDataSource.fetchFutureWeather(locationProvider.getPreferredLocationString())
    }


    private fun isFetchCurrentNeeded(lastFetchTime : ZonedDateTime): Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
    private fun isFetchFutureNeeded(): Boolean {
        val today = LocalDate.now()
        val futureWeatherCount = futureWeatherDao.countFutureWeather(today)
        return futureWeatherCount < FORECAST_DAYS_COUNT
    }

}