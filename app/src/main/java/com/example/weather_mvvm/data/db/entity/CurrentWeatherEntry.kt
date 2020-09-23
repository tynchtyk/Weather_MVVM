package com.example.weather_mvvm.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
const val CURRENT_WEATHER_ID = 0
@Entity (tableName = "current_weather")
@TypeConverters(ListTypeConverters::class)
data class CurrentWeatherEntry(
    val feelslike: Double,

    val precip: Double,

    val temparature: Double,

    @SerializedName("uv_index")
    val uvIndex: Double,

    val visibility: Double,

    @SerializedName("weather_code")
    val weatherCode: Int,

    @SerializedName("weather_descriptions")
    val weatherDescriptions: List<String>,

    @SerializedName("weather_icons")
    val weatherIcons: List<String>,

    @SerializedName("wind_dir")
    val windDir: String,

    @SerializedName("wind_speed")
    val windSpeed: Double,

    @PrimaryKey(autoGenerate = false)
    val id:Int = CURRENT_WEATHER_ID

)