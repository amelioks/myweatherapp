package com.ameliok.myweatherapp.screen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ameliok.myweatherapp.api.model.WeatherForecast
import com.ameliok.myweatherapp.api.model.WeatherForecastResponse
import com.ameliok.myweatherapp.data.SharedPreferenceHelper
import com.ameliok.myweatherapp.data.WeatherRepository
import kotlinx.coroutines.launch


class WeatherForecastViewModel(
    private val repository: WeatherRepository,
    private val sharedPreferenceHelper: SharedPreferenceHelper
) : ViewModel() {
    private val _weatherDataResult = MutableLiveData<WeatherForecastResponse>()
    val weatherDataResult: LiveData<WeatherForecastResponse> get() = _weatherDataResult

    private val _navigateToSelectedData = MutableLiveData<WeatherForecast?>()
    val navigateToSelectedData: MutableLiveData<WeatherForecast?>
        get() = _navigateToSelectedData

    var query: String = sharedPreferenceHelper.query
        private set

    init {
        getForecastData()
    }

    fun setNewQuery(newQuery: String) {
        query = newQuery
        sharedPreferenceHelper.query = newQuery
    }

    fun getForecastData() = viewModelScope.launch {
        if (query.isBlank()) {
            query = DEFAULT_QUERY
        }
        _weatherDataResult.value = repository.getWeatherForecastData(query, 9, "metric")
    }

    fun getForecastCurrentLocationData(latitude: Double, longitude: Double) =
        viewModelScope.launch {
            val result = repository.getWeatherForecastCurrentLocationData(
                latitude, longitude, 9, "metric"
            )
            _weatherDataResult.value = result
            setNewQuery(result.city.name)
        }

    fun onDataWeatherForecastClick(weatherForecast: WeatherForecast) {
        _navigateToSelectedData.value = weatherForecast
    }

    fun dataWeatherForecastNavigated() {
        _navigateToSelectedData.value = null
    }

    companion object {
        private const val DEFAULT_QUERY = "Berlin"
    }

}