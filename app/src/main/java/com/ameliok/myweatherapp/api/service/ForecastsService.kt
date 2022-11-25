package com.ameliok.myweatherapp.api.service

import com.ameliok.myweatherapp.api.model.WeatherForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastsService {

    @GET("data/2.5/forecast")
    suspend fun getForecasts(
        @Query("q") query: String,
        @Query("cnt") count: Int,
        @Query("units") page: String,
    ): WeatherForecastResponse

    @GET("data/2.5/forecast")
    suspend fun getForecastsCurrentLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") count: Int,
        @Query("units") page: String,
    ): WeatherForecastResponse

}