package com.ameliok.myweatherapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ameliok.myweatherapp.api.model.WeatherForecast
import com.ameliok.myweatherapp.database.DatabaseConstants
import com.ameliok.myweatherapp.database.WeatherForecastEntity

@Dao
interface WeatherForecastDao {
    @Query("SELECT * FROM ${DatabaseConstants.TABLE_NAME} ORDER by dt")
    fun getWeatherForecastData(): LiveData<List<WeatherForecastEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroids(weathers: List<WeatherForecastEntity>)


}