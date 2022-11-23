package com.ameliok.myweatherapp.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ameliok.myweatherapp.api.model.WeatherForecast
import com.ameliok.myweatherapp.api.model.WeatherForecastResponse
import com.ameliok.myweatherapp.api.service.ForecastsService
import com.ameliok.myweatherapp.data.WeatherRepository
import com.ameliok.myweatherapp.database.WeatherForecastEntity
import kotlinx.coroutines.launch

class WeatherForecastViewModel (
    private val repository: WeatherRepository
): ViewModel() {
    private val _weatherDataResult = MutableLiveData<List<WeatherForecast>>()
    val weatherDataResult: LiveData<List<WeatherForecast>>get() = _weatherDataResult

    private val _navigateToSelectedData = MutableLiveData<WeatherForecast?>()
    val navigateToSelectedData: MutableLiveData<WeatherForecast?>
        get() = _navigateToSelectedData

    var query: String = "berlin"
        private set

    init {
        getForecastData()
    }

    fun setNewQuery(newQuery: String) {
        query = newQuery
    }

    fun getForecastData() = viewModelScope.launch{
        _weatherDataResult.value = repository.getWeatherForecastData(query,9, "metric")
    }

    fun onDataWeatherForecastClick(weatherForecast: WeatherForecast) {
        _navigateToSelectedData.value = weatherForecast
    }

    fun dataWeatherForecastNavigated() {
        _navigateToSelectedData.value = null
    }

}