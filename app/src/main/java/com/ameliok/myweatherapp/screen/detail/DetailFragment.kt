package com.ameliok.myweatherapp.screen.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.ameliok.myweatherapp.MainActivity
import com.ameliok.myweatherapp.databinding.FragmentWeatherDetailBinding
import com.ameliok.myweatherapp.utils.setWeatherIconUrl
import com.ameliok.myweatherapp.utils.toDegree

class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentWeatherDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val weatherDetail = DetailFragmentArgs.fromBundle(
            requireArguments()
        ).selectedWeatherForecast

        binding.ImageWeather.setWeatherIconUrl(weatherDetail.weather.firstOrNull()?.icon)
        binding.RealFeelTemperature.text = weatherDetail.main.feelsLike.toDegree()
        binding.temperatureMax.text = weatherDetail.main.tempMax.toDegree()
        binding.temperatureMin.text = weatherDetail.main.tempMin.toDegree()
        binding.humidityLevel.text = weatherDetail.main.humidity.toString()

        val motionLayout = binding.motionLayout
        motionLayout.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

        })

        return binding.root
    }
}