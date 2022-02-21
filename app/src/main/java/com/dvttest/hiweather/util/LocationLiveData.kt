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
package com.dvttest.hiweather.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import com.dvttest.hiweather.data.datastore.HiWeatherStore
import com.dvttest.hiweather.data.models.UserLocation
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson

class LocationLiveData constructor(val context: Context) : LiveData<UserLocation>() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                updateUserLocation(location)
            }
        }
    }

    private fun checkPermissionsGranted(): Boolean =
        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        if (checkPermissionsGranted()) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.also {
                    updateUserLocation(location)
                }
            }
            startLocationUpdate()
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdate() {
        if (checkPermissionsGranted()) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    private fun updateUserLocation(location: Location) {
        val currentLocation = UserLocation(
            latitude = location.latitude,
            longitude = location.longitude,
        )
        storeCurrentLocation(currentLocation)
        value = currentLocation
    }

    /**
     * Store current location to Datastore
     **/
    private fun storeCurrentLocation(currentLocation: UserLocation) {
        val locationJson = Gson().toJson(currentLocation)
        HiWeatherStore.currentLocation = locationJson
    }

    override fun onInactive() {
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
}
