package com.example.weather_mvvm

import android.app.Application
import com.example.weather_mvvm.data.db.CurrentWeatherDataBase
import com.example.weather_mvvm.data.network.*
import com.example.weather_mvvm.data.repository.WeatherRepository
import com.example.weather_mvvm.data.repository.WeatherRepositoryImpl
import com.example.weather_mvvm.ui.weather.current.CurrentWeatherViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import okhttp3.internal.connection.ConnectInterceptor
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class WeatherApplication : Application(), KodeinAware{
    override val kodein = Kodein.lazy{
        import(androidXModule(this@WeatherApplication))

        bind() from singleton { CurrentWeatherDataBase(instance())}
        bind() from singleton { instance<CurrentWeatherDataBase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWeatherApiService(instance())}
        bind<WeatherNetworkDataSource>() with singleton{ WeatherNetworkDataSourceImpl(instance()) }
        bind<WeatherRepository>() with singleton{ WeatherRepositoryImpl(instance(), instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }


    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}