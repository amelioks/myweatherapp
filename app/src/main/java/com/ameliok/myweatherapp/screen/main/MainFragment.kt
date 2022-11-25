package com.ameliok.myweatherapp.screen.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.appbar.AppBarLayout

class MainFragment: Fragment() {
    private val repository = WeatherRepository(ServiceBuilder(ForecastsService::class.java))
    private val sharedPreferenceHelper by lazy { SharedPreferenceHelper(requireContext()) }
    private val viewModel: WeatherForecastViewModel by viewModels { WeatherForecastViewModelFactory(repository, sharedPreferenceHelper) }
    private lateinit var adapter: WeatherForecastAdapter
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val REQUEST_LOCATION = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {
            Toast.makeText(context, "Permission not granted!", Toast.LENGTH_SHORT).show();
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null.
                if (location != null){
                    viewModel.getForecastCurrentLocationData(location.latitude,location.longitude)
                } else {
                    Toast.makeText(context, "Location not found", Toast.LENGTH_SHORT).show();
                }
            }

        coordinateMotion()
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        bindUI()
        initQuery()
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

    private fun coordinateMotion() {

        val appBarLayout = binding.appBarLayout
        val motionLayout = binding.motionLayout

        val listener = AppBarLayout.OnOffsetChangedListener { unused, verticalOffset ->
            val seekPosition = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
            motionLayout.progress = seekPosition
        }

        appBarLayout.addOnOffsetChangedListener(listener)
    }

}
