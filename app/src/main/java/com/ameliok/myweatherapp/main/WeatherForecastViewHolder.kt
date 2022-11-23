package com.ameliok.myweatherapp.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ameliok.myweatherapp.api.model.WeatherForecast
import com.ameliok.myweatherapp.databinding.WeatherListBinding
import com.ameliok.myweatherapp.utils.getTemperatureRangeText
import com.ameliok.myweatherapp.utils.getWeatherDateText
import com.ameliok.myweatherapp.utils.setWeatherIconUrl

class WeatherForecastViewHolder(val binding: WeatherListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WeatherForecast, onClick: WeatherForecastAdapter.OnClickListener) {
        itemView.apply {
            val weather = item.weather.firstOrNull()
            binding.weatherDescription.text = weather?.description
            binding.weatherDate.text = item.getWeatherDateText()
            binding.weatherIcon.setWeatherIconUrl(weather?.icon)
            binding.weatherTemperature.text = item.main.getTemperatureRangeText()
            binding.executePendingBindings()
            setOnClickListener { onClick.clickListener(item) }
        }
    }

}