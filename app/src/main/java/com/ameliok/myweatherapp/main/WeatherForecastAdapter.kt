package com.ameliok.myweatherapp.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ameliok.myweatherapp.api.model.WeatherForecast
import com.ameliok.myweatherapp.databinding.WeatherListBinding

class WeatherForecastAdapter(onClickListener: OnClickListener)
    : ListAdapter<WeatherForecast, WeatherForecastViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<WeatherForecast>() {
        override fun areItemsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
        val weather = getItem(position)
        holder.bind(weather)
    }

    class OnClickListener(val clickListener: (weather: WeatherForecast) -> Unit) {
        fun onClick(weather: WeatherForecast) = clickListener(weather)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = WeatherListBinding.inflate(layoutInflater, parent, false)
        return WeatherForecastViewHolder(binding)
    }

}