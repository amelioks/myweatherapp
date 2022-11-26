package com.ameliok.myweatherapp.screen.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    private lateinit var binding: FragmentWeatherDetailBinding

    private var progress = 0F

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentWeatherDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val weatherDetail = DetailFragmentArgs.fromBundle(
            requireArguments()
        ).selectedWeatherForecast

        binding.ImageWeather.setWeatherIconUrl(weatherDetail.weather.firstOrNull()?.icon)
        binding.RealFeelTemperature.text = weatherDetail.main.feelsLike.toDegree()
        binding.temperatureMax.text = weatherDetail.main.tempMax.toDegree()
        binding.temperatureMin.text = weatherDetail.main.tempMin.toDegree()
        binding.humidityLevel.text = weatherDetail.main.humidity.toString()

//        binding.motionLayout.progress = savedInstanceState?.getFloat(KEY_MOTION_PROGRESS) ?: 0f
        binding.motionLayout.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                Log.d(TAG, "onTransitionStarted: ")
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                Log.d(TAG, "onTransitionChange: ")
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                //startActivity(Intent(requireActivity(), MainActivity::class.java))
                Log.d(TAG, "onTransitionCompleted: ")
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
                Log.d(TAG, "onTransitionTrigger: ")
            }

        })

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putFloat(KEY_MOTION_PROGRESS, binding.motionLayout.progress)
    }

    companion object {
        private const val KEY_MOTION_PROGRESS = "motion_progress"
        private const val TAG = "detailFragment"
    }
}