package com.example.weather_mvvm.data.provider

import android.content.Context
import android.util.Log
import com.example.weather_mvvm.internals.UnitSystem

const val UNIT_SYSTEM = "UNIT_SYSTEM"

class UnitProviderImpl(context: Context) : PreferenceProvider(context), UnitProvider {

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        Log.e("UNIST SYSTEM", selectedName.toString())
        return UnitSystem.valueOf(selectedName!!)
    }
}
