package com.ameliok.myweatherapp.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ameliok.myweatherapp.api.model.WeatherForecastResponse
import com.ameliok.myweatherapp.api.service.ForecastsService
import com.ameliok.myweatherapp.data.WeatherRepository

//class WeatherAppViewModel (
//    private val repository: WeatherRepository
//): ViewModel() {
//    private val _weatherDataResult = MutableLiveData<ResultLoad<WeatherForecastResponse>>()
//    val weatherDataResult: LiveData<ResultLoad<WeatherForecastResponse>> get() = _weatherDataResult
//
//}