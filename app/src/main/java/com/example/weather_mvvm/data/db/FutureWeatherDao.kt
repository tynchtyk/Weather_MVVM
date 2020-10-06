package com.example.weather_mvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather_mvvm.data.db.entity.FutureWeatherEntry
import com.example.weather_mvvm.data.db.unitlocalized.future.list.ImperialSimpleFutureWeatherEntry
import com.example.weather_mvvm.data.db.unitlocalized.future.list.MetricSimpleFutureWeatherEntry
import java.time.LocalDate

@Dao
interface FutureWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherEntries: List<FutureWeatherEntry>)

    @Query("select * from future_weather where date(date) >= date(:startDate)")
    fun getSimpleWeatherForecastMetric(startDate:org.threeten.bp.LocalDate): LiveData<List<MetricSimpleFutureWeatherEntry>>

    @Query("select * from future_weather where date(date) >= date(:startDate)")
    fun getSimpleWeatherForecastImperial(startDate:org.threeten.bp.LocalDate): LiveData<List<ImperialSimpleFutureWeatherEntry>>

    @Query("select count(id) from future_weather where date(date) >= date(:startDate)")
    fun countFutureWeather(startDate: org.threeten.bp.LocalDate): Int

    @Query("delete from future_weather where date(date) < date(:firstDateToKeep)")
    fun deleteOldEntries(firstDateToKeep: org.threeten.bp.LocalDate)

}