package com.ameliok.myweatherapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ameliok.myweatherapp.api.model.City
import com.ameliok.myweatherapp.api.model.Wind

@Entity(tableName = DatabaseConstants.TABLE_NAME)
data class WeatherForecastEntity(
    @PrimaryKey
    val city: City,
    val dt: Long,
    val temp: Double,
    val feelsLike: Double,
    val tempMax: Double,
    val tempMin: Double,
    val wind: Wind,
    val humidity: Int,
    val pressure: Int,
    val description: String
)
