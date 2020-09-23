package com.example.weather_mvvm.data.db.entity

import android.util.Log
import androidx.room.TypeConverter

object ListTypeConverters {
    @TypeConverter
    @JvmStatic
    fun stringToStringList(data: String?): List<String>? {
        return data?.let {
            it.split(",").map {
                try {
                    it.toString()
                } catch (ex: NumberFormatException) {
                    Log.e("Converter","Cannot convert $it to number")
                    null
                }
            }
        }?.filterNotNull()
    }

    @TypeConverter
    @JvmStatic
    fun StringListToString(ints: List<String>?): String? {
        return ints?.joinToString(",")
    }
}