package com.ameliok.myweatherapp.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ameliok.myweatherapp.databinding.FragmentMainBinding

class MainFragment: Fragment() {
    private val viewModel: WeatherAppViewModel by lazy {
        ViewModelProvider(this).get(WeatherAppViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}