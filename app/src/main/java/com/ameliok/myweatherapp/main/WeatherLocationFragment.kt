package com.ameliok.myweatherapp.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.ameliok.myweatherapp.databinding.FragmentMainBinding
import com.ameliok.myweatherapp.databinding.FragmentWeatherDetailBinding
import com.ameliok.myweatherapp.databinding.FragmentWeatherLocationBinding

class WeatherLocationFragment : Fragment() {
    private val viewModel: WeatherForecastViewModel by viewModels()
    private lateinit var adapter: WeatherForecastAdapter
    private var _binding: FragmentWeatherLocationBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWeatherLocationBinding.inflate(inflater, container,false)
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindUI()
    }

    fun bindUI() {
        setupSearchView()

    }

    private fun setupSearchView() {
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setNewQuery(query ?: "")
                viewModel.getForecastData()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}