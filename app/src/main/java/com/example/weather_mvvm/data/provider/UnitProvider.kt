package com.example.weather_mvvm.data.provider

import com.example.weather_mvvm.internals.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}