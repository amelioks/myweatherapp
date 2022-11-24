package com.ameliok.myweatherapp.screen.main

import android.os.Bundle
import android.view.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ameliok.myweatherapp.api.service.ForecastsService
import com.ameliok.myweatherapp.api.service.ServiceBuilder
import com.ameliok.myweatherapp.data.SharedPreferenceHelper
import com.ameliok.myweatherapp.data.WeatherRepository
import com.ameliok.myweatherapp.databinding.FragmentMainBinding
import com.ameliok.myweatherapp.utils.toDegree

class MainFragment: Fragment() {
    private val repository = WeatherRepository(ServiceBuilder(ForecastsService::class.java))
    private val sharedPreferenceHelper by lazy { SharedPreferenceHelper(requireContext()) }
    private val viewModel: WeatherForecastViewModel by viewModels { WeatherForecastViewModelFactory(repository, sharedPreferenceHelper) }
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
     //   initQuery()
    }

    private fun initQuery() {
        val arg = arguments ?: return
        val query = MainFragmentArgs.fromBundle(arg).changeLocationClick?: return
        viewModel.setNewQuery(query)
        viewModel.getForecastData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.weatherDataResult.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.list)
            binding.currentLocation.text = it.city.name
            binding.currentTemperature.text = it.list.firstOrNull()?.main?.temp?.toDegree()
        })
    }

    fun bindUI(){
        setupView()
        changeLocationClick()
    }

    private fun setupView(): View {
        adapter = WeatherForecastAdapter(WeatherForecastAdapter.OnClickListener { forecastID
            -> viewModel.onDataWeatherForecastClick(forecastID)
        })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.navigateToSelectedData.observe(viewLifecycleOwner) {forecast ->
            forecast?.let{
                findNavController().navigate(MainFragmentDirections.actionShowDetail(
                        it
                    )
                )
                viewModel.dataWeatherForecastNavigated()
            }
        }
        return binding.root
    }

    fun changeLocationClick() {
        binding.changeLocation.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToWeatherLocationFragment())
        }
    }
}
