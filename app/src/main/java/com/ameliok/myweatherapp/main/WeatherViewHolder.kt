package com.ameliok.myweatherapp.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ameliok.myweatherapp.api.model.WeatherForecast
import com.ameliok.myweatherapp.databinding.WeatherListBinding

class WeatherViewHolder private constructor(private val binding: WeatherListBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(weather: WeatherForecast, clickListener: WeatherAdapter.OnClickListener) {
        binding.weather = weather
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun createView(parent: ViewGroup): WeatherViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = WeatherListBinding.inflate(layoutInflater, parent, false)
            return WeatherViewHolder(binding)
        }
    }
}