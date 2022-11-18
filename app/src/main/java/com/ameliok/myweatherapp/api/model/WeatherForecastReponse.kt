package com.ameliok.myweatherapp.api.model

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("list")
    val list: List<WeatherForecast>,
    @SerializedName("message")
    val message: String
)