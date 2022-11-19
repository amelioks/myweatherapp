package com.ameliok.myweatherapp.api.service

import com.ameliok.myweatherapp.api.model.WeatherForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastsService {

    @GET("data/2.5/forecast")
    suspend fun getForecasts(
        @Query("q") query: String,
        @Query("cnt") count: Int
    ): WeatherForecastResponse

}