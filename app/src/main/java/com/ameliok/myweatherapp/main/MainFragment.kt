package com.ameliok.myweatherapp.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ameliok.myweatherapp.api.model.WeatherForecast
import com.ameliok.myweatherapp.api.service.ForecastsService
import com.ameliok.myweatherapp.api.service.ServiceBuilder
import com.ameliok.myweatherapp.data.WeatherRepository
import com.ameliok.myweatherapp.databinding.FragmentMainBinding

class MainFragment: Fragment() {
    private val repository = WeatherRepository(ServiceBuilder(ForecastsService::class.java))
    private val viewModel: WeatherForecastViewModel by viewModels { WeatherForecastViewModelFactory(repository) }
    private lateinit var adapter: WeatherForecastAdapter

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        bindUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.weatherDataResult.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    fun bindUI(){
        setupView()
        setupSearchView()
    }

    private fun setupView() {
        adapter = WeatherForecastAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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