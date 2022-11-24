package com.ameliok.myweatherapp.data

import com.ameliok.myweatherapp.api.model.WeatherForecast
import com.ameliok.myweatherapp.api.model.WeatherForecastResponse
import com.ameliok.myweatherapp.api.service.ForecastsService

class WeatherRepository(
    private val service: ForecastsService
) {
    suspend fun getWeatherForecastData(
        query: String,
        forecastDayCount: Int,
        units: String
    ): WeatherForecastResponse {
        return service.getForecasts(query, forecastDayCount, units)
    }
}