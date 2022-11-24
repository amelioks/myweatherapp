package com.ameliok.myweatherapp.main

import android.os.Bundle
import android.view.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
            adapter.submitList(it.list)
            binding.currentLocation.text = it.city.name
            binding.currentTemperature.text = it.list.firstOrNull()?.main?.temp.toString()
        })
    }

    fun bindUI(){
        setupView()
        //setupSearchView()
    }

    private fun setupView(): View {
        adapter = WeatherForecastAdapter(WeatherForecastAdapter.OnClickListener { forecastID
            -> viewModel.onDataWeatherForecastClick(forecastID)
        })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.navigateToSelectedData.observe(viewLifecycleOwner) {forecast ->
            forecast?.let{
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.dataWeatherForecastNavigated()
            }
        }
        return binding.root
    }

//    private fun setupSearchView() {
//        binding.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                viewModel.setNewQuery(query ?: "")
//                viewModel.getForecastData()
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//        })
//    }
}
