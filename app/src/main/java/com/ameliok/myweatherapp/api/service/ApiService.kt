package com.ameliok.myweatherapp.api.service

import com.ameliok.myweatherapp.api.interceptor.ApiInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    operator fun <T> invoke(serviceClass: Class<T>): T {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ApiInterceptor())
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(serviceClass)
    }

    // sample request :
    // http://api.openweathermap.org/data/2.5/forecast?q=jakarta&cnt=5&units=metric&appId=8828e93aa68573092492094256559cb5
    private const val BASE_ENDPOINT: String = "https://api.openweathermap.org/"
    const val APP_ID: String = "e845710d35b979e3b383b020fbc48ff8"
}