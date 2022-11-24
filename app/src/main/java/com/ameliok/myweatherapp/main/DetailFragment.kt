package com.ameliok.myweatherapp.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ameliok.myweatherapp.databinding.FragmentWeatherDetailBinding
import com.ameliok.myweatherapp.utils.setImageUrl
import com.ameliok.myweatherapp.utils.setWeatherIconUrl

class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentWeatherDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val weatherDetail = DetailFragmentArgs.fromBundle(requireArguments()).selectedWeatherForecast

        binding.ImageWeather.setWeatherIconUrl(weatherDetail.weather.firstOrNull()?.icon)
        binding.RealFeelTemperature.text = weatherDetail.main.feelsLike.toString()
        binding.temperatureMax.text = weatherDetail.main.tempMax.toString()
        binding.temperatureMin.text = weatherDetail.main.tempMin.toString()
        binding.humidityLevel.text = weatherDetail.main.humidity.toString()

        return binding.root
    }
}