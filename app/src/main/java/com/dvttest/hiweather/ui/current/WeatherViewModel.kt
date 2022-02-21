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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvttest.hiweather.core.extensions.asLiveData
import com.dvttest.hiweather.data.api.State
import com.dvttest.hiweather.data.db.entities.Forecast
import com.dvttest.hiweather.data.db.entities.Weather
import com.dvttest.hiweather.data.models.UserLocation
import com.dvttest.hiweather.domain.usecases.LocationForecastUseCase
import com.dvttest.hiweather.domain.usecases.LocationWeatherUseCase
import com.dvttest.hiweather.util.LocationLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val locationWeatherUseCase: LocationWeatherUseCase,
    private val locationForecastUseCase: LocationForecastUseCase
) : ViewModel() {

    @Inject
    lateinit var locationLiveData: LocationLiveData
    fun fetchLocationLiveData() = locationLiveData

    private val _currentWeather = MutableLiveData<Weather?>()
    val currentWeather = _currentWeather.asLiveData()

    private val _forecast = MutableLiveData<List<Forecast>?>()
    val forecast = _forecast.asLiveData()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading.asLiveData()

    fun getCurrentLocationWeather(currentLocation: UserLocation) {
        viewModelScope.launch {
            locationWeatherUseCase.invoke(currentLocation).collect { state ->
                when (state) {
                    is State.Loading -> {
                        _loading.value = true
                        if (state.data != null) {
                            _currentWeather.value = state.data
                        }
                    }
                    is State.Success -> {
                        _loading.value = false
                        if (state.data != null) {
                            _currentWeather.value = state.data
                        }
                    }
                    is State.Error -> {
                        _loading.value = false
                    }
                }
            }
        }
        getCurrentLocationForecast(currentLocation)
    }

    fun getCurrentLocationForecast(currentLocation: UserLocation) {
        viewModelScope.launch {
            locationForecastUseCase.invoke(currentLocation).collect { state ->
                when (state) {
                    is State.Loading -> {
                        _loading.value = true
                        if (state.data != null) {
                            _forecast.value = state.data
                        }
                    }
                    is State.Success -> {
                        _loading.value = false
                        if (state.data != null) {
                            _forecast.value = state.data
                        } else {
                            _forecast.value = listOf()
                        }
                    }
                    is State.Error -> {
                        _loading.value = false
                    }
                }
            }
        }
    }
}
