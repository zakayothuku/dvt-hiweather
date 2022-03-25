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
package com.dvttest.hiweather.data.respositories

import com.dvttest.hiweather.data.BuildConfig
import com.dvttest.hiweather.data.api.NetworkBoundResource.networkBoundResource
import com.dvttest.hiweather.data.api.State
import com.dvttest.hiweather.data.api.WeatherService
import com.dvttest.hiweather.data.db.dao.WeatherDao
import com.dvttest.hiweather.data.db.entities.Forecast
import com.dvttest.hiweather.data.db.entities.Weather
import com.dvttest.hiweather.data.mapper.CurrentForecastMapper
import com.dvttest.hiweather.data.mapper.CurrentWeatherMapper
import com.dvttest.hiweather.data.models.UserLocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao,
) : WeatherRepository {

    override fun getCurrentLocationWeather(userLocation: UserLocation): Flow<State<Weather?>> {
        val weatherMapper = CurrentWeatherMapper()
        return networkBoundResource(
            query = {
                weatherDao.getLastUpdatedWeather()
            },
            fetch = {
                weatherService.getCurrentLocationWeather(
                    latitude = userLocation.latitude,
                    longitude = userLocation.longitude,
                    key = BuildConfig.OPEN_WEATHER_API_KEY
                )
            },
            saveFetchResponse = { response ->
                weatherDao.deleteWeatherData()
                weatherDao.saveCurrentLocationWeather(
                    weatherMapper.transformToDomain(response)
                )
            }
        )
    }

    override fun getCurrentLocationForecast(userLocation: UserLocation): Flow<State<List<Forecast>?>> {
        val weatherMapper = CurrentForecastMapper()
        return networkBoundResource(
            query = {
                weatherDao.getLastUpdatedForecast()
            },
            fetch = {
                weatherService.getCurrentLocationForecast(
                    latitude = userLocation.latitude,
                    longitude = userLocation.longitude,
                    key = BuildConfig.OPEN_WEATHER_API_KEY
                )
            },
            saveFetchResponse = { response ->
                weatherDao.deleteForecastData()
                weatherDao.saveCurrentLocationForecast(
                    weatherMapper.transformToDomain(response)
                )
            }
        )
    }
}
