package com.example.weather_mvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather_mvvm.data.network.response.CurrentWeatherResponse
import com.example.weather_mvvm.internals.NoConnectivityException

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
        }
        catch (
            e: NoConnectivityException
        ){
            Log.e("Connectivity","No connection")
        }
    }
}