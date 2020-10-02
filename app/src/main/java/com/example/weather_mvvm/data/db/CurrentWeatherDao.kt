package com.example.weather_mvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather_mvvm.data.db.entity.CURRENT_WEATHER_ID
import com.example.weather_mvvm.data.db.entity.CurrentWeatherEntry
import com.example.weather_mvvm.data.db.unitlocalized.current.ImperialCurrentWeatherEntry
import com.example.weather_mvvm.data.db.unitlocalized.current.MetricCurrentWeatherEntry
import kotlinx.coroutines.selects.select

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry>

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>

}