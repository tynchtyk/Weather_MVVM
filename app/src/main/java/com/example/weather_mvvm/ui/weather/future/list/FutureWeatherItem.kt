package com.example.weather_mvvm.ui.weather.future.list

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather_mvvm.R
import com.example.weather_mvvm.data.db.unitlocalized.future.list.MetricSimpleFutureWeatherEntry
import com.example.weather_mvvm.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_future_weather.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle


class FutureWeatherItem(
    val weatherEntry: UnitSpecificSimpleFutureWeatherEntry
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            textView_date.text = weatherEntry.conditionText
            updateDate()
            updateTemperature()
            updateConditionImage()
        }
    }

    override fun getLayout() = R.layout.item_future_weather

    private fun GroupieViewHolder.updateDate() {
        val dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        textView_date.text = weatherEntry.date.format(dtFormatter)
    }

    private fun GroupieViewHolder.updateTemperature() {
        val unitAbbreviation = if (weatherEntry is MetricSimpleFutureWeatherEntry) "°C"
        else "°F"
        textView_temperature.text = "${weatherEntry.avgTemperature}$unitAbbreviation"
    }

    private fun GroupieViewHolder.updateConditionImage() {
        Glide.with(this.containerView)
            .load("http:" + weatherEntry.conditionIconUrl)
            .into(imageView_condition_icon)
    }
}
