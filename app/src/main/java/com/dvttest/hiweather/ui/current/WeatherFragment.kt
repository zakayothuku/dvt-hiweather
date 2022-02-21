/*
 * Copyright (C) 2022. dvt.co.za
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dvttest.hiweather.ui.current

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.dvttest.hiweather.R
import com.dvttest.hiweather.core.base.BaseFragment
import com.dvttest.hiweather.core.extensions.observe
import com.dvttest.hiweather.core.extensions.observeOnce
import com.dvttest.hiweather.data.datastore.HiWeatherStore
import com.dvttest.hiweather.databinding.FragmentWeatherBinding
import com.dvttest.hiweather.ui.forecast.ForecastAdapter
import com.dvttest.hiweather.worker.UpdateWeatherWorker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class WeatherFragment : BaseFragment<FragmentWeatherBinding>(R.layout.fragment_weather) {

    private val viewModel: WeatherViewModel by viewModels()
    private val forecastAdapter by lazy { ForecastAdapter() }
    lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) ||
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    getCurrentLocationWeather(false)
                }
                else -> {
                    showLocationPermissionsDialog()
                }
            }
        }
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.refresh.setOnRefreshListener { getCurrentLocationWeather(refresh = true) }
        viewBinding.weatherForecast.adapter = forecastAdapter

        observe(viewModel.loading) { loading ->
            viewBinding.refresh.isRefreshing = loading
        }

        getCurrentLocationWeather(false)
    }

    private fun checkPermissionsGranted(): Boolean =
        ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    /**
     * Get current weather for Location.
     * @param refresh Boolean to show if should refresh weather or not.
     **/
    private fun getCurrentLocationWeather(refresh: Boolean) {
        if (checkPermissionsGranted()) {
            with(viewModel) {
                fetchLocationLiveData().observeOnce(viewLifecycleOwner) { currentLocation ->
                    if (currentLocation != null) {
                        HiWeatherStore.currentLocation = currentLocation.toString()
                        currentLocation.refresh = refresh
                        setupWeatherWorker()
                        getCurrentLocationWeather(currentLocation)
                    }
                }
            }
        } else {
            showLocationPermissionsDialog()
        }
    }

    private fun showLocationPermissionsDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.location_permissions)
            .setMessage(resources.getString(R.string.location_permissions_description))
            .setPositiveButton(resources.getString(R.string.action_accept)) { _, _ ->
                getCurrentLocation()
            }
            .setNegativeButton(resources.getString(R.string.action_decline)) { _, _ -> }
            .show()
    }

    /**
     * Check if Location Permissions are granted before getting current Location
     **/
    private fun getCurrentLocation() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    /**
     * Setup worker for fetching periodic weather forecasts.
     **/
    private fun setupWeatherWorker() {
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<UpdateWeatherWorker>(3, TimeUnit.HOURS)
            .setConstraints(constraint)
            .setInitialDelay(1, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "update_weather_forecast",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }
}
