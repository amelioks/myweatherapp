package com.ameliok.myweatherapp.data

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ameliok.myweatherapp.api.model.WeatherForecast
import com.ameliok.myweatherapp.database.DatabaseConstants

interface WeatherAppDao {
    @Query("SELECT * FROM ${DatabaseConstants.TABLE_NAME} ORDER by closeApproachDate")
    fun getWeatherForecastData(): LiveData<List<WeatherForecast>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroids(weathers: List<WeatherForecast>)


}