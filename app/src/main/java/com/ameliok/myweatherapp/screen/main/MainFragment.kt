package com.ameliok.myweatherapp.screen.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ameliok.myweatherapp.api.service.ForecastsService
import com.ameliok.myweatherapp.api.service.ServiceBuilder
import com.ameliok.myweatherapp.data.SharedPreferenceHelper
import com.ameliok.myweatherapp.data.WeatherRepository
import com.ameliok.myweatherapp.databinding.FragmentMainBinding
import com.ameliok.myweatherapp.utils.toDegree
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MainFragment : Fragment() {
    private val repository = WeatherRepository(ServiceBuilder(ForecastsService::class.java))
    private val sharedPreferenceHelper by lazy { SharedPreferenceHelper(requireContext()) }
    private val viewModel: WeatherForecastViewModel by viewModels {
        WeatherForecastViewModelFactory(
            repository,
            sharedPreferenceHelper
        )
    }
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
        if (arg.isEmpty) return
        val query = MainFragmentArgs.fromBundle(arg).changeLocationClick ?: return
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
        viewModel.navigateToSelectedData.observe(viewLifecycleOwner) { forecast ->
            forecast?.let {
                findNavController().navigate(
                    MainFragmentDirections.actionShowDetail(it)
                )
                viewModel.dataWeatherForecastNavigated()
            }
        }
    }

    fun bindUI() {
        setupView()
    }

    private fun setupView(): View {
        adapter = WeatherForecastAdapter(WeatherForecastAdapter.OnClickListener { forecastID
            ->
            viewModel.onDataWeatherForecastClick(forecastID)
        })
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.useMyLocation.setOnClickListener {
            onMyLocationClicked()
        }
        binding.changeLocation.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToWeatherLocationFragment())
        }

        return binding.root
    }


    private fun isPermissionGranted(): Boolean {
        val isFineLocationGranted = ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val isCoarseLocationGranted = ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return isFineLocationGranted && isCoarseLocationGranted

    }

    private fun onMyLocationClicked() {
        if (isPermissionGranted()) {
            getLocation()
        } else {
            requestPermissions(
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // permission was granted.
                    getLocation()
                } else {
                    // permission denied.
                    // tell the user the action is cancelled
                    Toast.makeText(context, "Permission is not granted!", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    viewModel.getForecastCurrentLocationData(
                        location.latitude,
                        location.longitude
                    )
                } else {
                    Toast.makeText(context, "Location not found", Toast.LENGTH_SHORT).show();
                }
            }
    }
}

