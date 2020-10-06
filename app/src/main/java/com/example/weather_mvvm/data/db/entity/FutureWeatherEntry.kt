package com.example.weather_mvvm.data.db.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.weather_mvvm.data.db.entity.Day

@Entity(tableName = "future_weather", indices = [Index(value = ["date"], unique = true)])
data class FutureWeatherEntry(
    val date: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @Embedded
    val day: Day
) 