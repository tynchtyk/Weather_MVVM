package com.example.weather_mvvm

import android.app.Application
import android.content.Context
import android.location.LocationProvider
import com.example.weather_mvvm.data.db.WeatherDataBase
import com.example.weather_mvvm.data.network.*
import com.example.weather_mvvm.data.provider.LocationProviderImpl
import com.example.weather_mvvm.data.provider.UnitProvider
import com.example.weather_mvvm.data.provider.UnitProviderImpl
import com.example.weather_mvvm.data.repository.WeatherRepository
import com.example.weather_mvvm.data.repository.WeatherRepositoryImpl
import com.example.weather_mvvm.ui.weather.current.CurrentWeatherViewModelFactory
import com.example.weather_mvvm.ui.weather.future.list.FutureListWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class WeatherApplication : Application(), KodeinAware{
    override val kodein = Kodein.lazy{
        import(androidXModule(this@WeatherApplication))

        bind() from singleton { WeatherDataBase(instance())}
        bind() from singleton { instance<WeatherDataBase>().currentWeatherDao() }
        bind() from singleton { instance<WeatherDataBase>().futureWeatherDao() }
        bind() from singleton { instance<WeatherDataBase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWeatherApiService(instance())}
        bind<WeatherNetworkDataSource>() with singleton{ WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider {LocationServices.getFusedLocationProviderClient(instance<Context>())}
        bind<com.example.weather_mvvm.data.provider.LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<WeatherRepository>() with singleton{ WeatherRepositoryImpl(instance(), instance(), instance(), instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance(), instance()) }


     }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}