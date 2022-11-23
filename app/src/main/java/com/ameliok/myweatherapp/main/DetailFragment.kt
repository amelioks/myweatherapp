package com.ameliok.myweatherapp.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ameliok.myweatherapp.databinding.FragmentWeatherDetailBinding

class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentWeatherDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }
}