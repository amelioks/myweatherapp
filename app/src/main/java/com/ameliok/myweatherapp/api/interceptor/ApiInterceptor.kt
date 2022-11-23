package com.ameliok.myweatherapp.api.interceptor

import android.util.Log
import com.ameliok.myweatherapp.api.service.ServiceBuilder.APP_ID
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("appId", APP_ID)
            .build()

        Log.d("test",url.toString())

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}