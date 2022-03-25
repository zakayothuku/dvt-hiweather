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
package com.dvttest.hiweather.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dvttest.hiweather.data.api.State
import com.dvttest.hiweather.data.datastore.HiWeatherStore
import com.dvttest.hiweather.data.db.entities.Favorite
import com.dvttest.hiweather.data.models.UserLocation
import com.dvttest.hiweather.data.respositories.FavoritesRepository
import com.dvttest.hiweather.data.respositories.WeatherRepository
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect

@HiltWorker
class UpdateWeatherWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val weatherRepository: WeatherRepository,
    private val favoritesRepository: FavoritesRepository,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        var result: Result = Result.success()
        val location = Gson().fromJson(HiWeatherStore.currentLocation, UserLocation::class.java)
        val userLocation = UserLocation(location.latitude, location.longitude)

        weatherRepository.getCurrentLocationWeather(userLocation).collect { weather ->
            when (weather) {
                is State.Success -> {
                    if (weather.data != null) {
                        favoritesRepository.deleteLocation(true)
                        favoritesRepository.saveLocation(
                            Favorite(
                                latitude = userLocation.latitude,
                                longitude = userLocation.longitude,
                                address = "My Location",
                                name = weather.data?.addressName!!,
                                weather = weather.data?.weather,
                                weatherIcon = weather.data?.weatherIcon,
                                temp = weather.data?.temp,
                                currentLocation = true
                            )
                        )

                        weatherRepository.getCurrentLocationForecast(userLocation).collect { forecast ->
                            result = when (forecast) {
                                is State.Success -> {
                                    if (forecast.data != null) {
                                        Result.success()
                                    } else {
                                        Result.failure()
                                    }
                                }
                                else -> {
                                    Result.failure()
                                }
                            }
                        }
                    } else {
                        result = Result.failure()
                    }
                }
                else -> {
                    result = Result.failure()
                }
            }
        }
        return result
    }
}
