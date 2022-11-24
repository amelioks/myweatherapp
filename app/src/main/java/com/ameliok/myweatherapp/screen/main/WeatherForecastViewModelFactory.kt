package com.ameliok.myweatherapp.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ameliok.myweatherapp.data.SharedPreferenceHelper
import com.ameliok.myweatherapp.data.WeatherRepository

class WeatherForecastViewModelFactory(
    private val repository: WeatherRepository,
    private val sharedPreferenceHelper: SharedPreferenceHelper
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherForecastViewModel(repository, sharedPreferenceHelper) as T
    }
}