package com.example.weather_mvvm.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.weather_mvvm.R
import com.example.weather_mvvm.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory : CurrentWeatherViewModelFactory by instance()


    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch{
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer
            Log.e("response", it.toString())
            group_loading.visibility = View.GONE

            updateLocation("Seoul")
            updateDay()
            updateTemperatures(it.temperature, it.feelslike)
            updateCondition(it.weatherDescriptions[0])
            updatePrecipitation(it.precip)
            updateWind(it.windDir, it.windSpeed)
            updateVisibility(it.visibility)
            Glide.with(this@CurrentWeatherFragment)
                .load(it.weatherIcons[0].toString())
                .into(imageView_condition_icon)

        })

    }

    private fun updateLocation(location : String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDay(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperatures(temperature : Int, feelslike : Int){
        textView_temperature.text = "$temperature°C"
        textView_feels_like_temperature.text = "Feels like $feelslike°C"
    }

    private fun updateCondition(condition : String){
        textView_condition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume : Int){
        textView_precipitation.text = "Precipitation $precipitationVolume mm"
    }

    private fun updateWind(windDirection : String, windSpeed : Int){
        textView_wind.text = "Wind: $windDirection $windSpeed kph"
    }

    private fun updateVisibility(visibility : Int) {
        textView_visibility.text = "Visibility : $visibility km"
    }




}