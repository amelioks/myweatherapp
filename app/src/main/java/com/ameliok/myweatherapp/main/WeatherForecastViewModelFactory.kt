package com.ameliok.myweatherapp.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ameliok.myweatherapp.data.WeatherRepository

class WeatherForecastViewModelFactory(
    private val repository: WeatherRepository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherForecastViewModel(repository) as T
    }
}