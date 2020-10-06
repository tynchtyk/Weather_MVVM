package com.example.weather_mvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather_mvvm.data.network.response.CurrentWeatherResponse
import com.example.weather_mvvm.data.network.response.FutureWeatherResponse
import com.example.weather_mvvm.internals.NoConnectivityException

const val FORECAST_DAYS_COUNT = 7
class WeatherNetworkDataSourceImpl(
    private val apixuWeatherApiService : ApixuWeatherApiService
) : WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String) {
        try{
            val fetchedCurrentWeather = apixuWeatherApiService.getCurrentWeather(location)
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
            Log.e("CURRENTAPIRESPONSE $location", fetchedCurrentWeather.toString())
        }
        catch (
            e: NoConnectivityException
        ){
            Log.e("Connectivity","No connection")
        }
    }

    private val _downloadedFutureWeather = MutableLiveData<FutureWeatherResponse>()
    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    override suspend fun fetchFutureWeather(location: String) {
        try{
            val fetchedFutureWeather = apixuWeatherApiService.getFutureWeather(location, days = FORECAST_DAYS_COUNT)
                .await()
            _downloadedFutureWeather.postValue(fetchedFutureWeather)
            Log.e("FUTUREAPIRESPONSE $location", fetchedFutureWeather.toString())
        }
        catch (
            e: NoConnectivityException
        ){
            Log.e("Connectivity","No connection")
        }
    }

}