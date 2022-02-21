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
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dvttest.hiweather.data.api.State
import com.dvttest.hiweather.data.datastore.HiWeatherStore
import com.dvttest.hiweather.data.models.UserLocation
import com.dvttest.hiweather.data.respositories.WeatherRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.last

class UpdateWeatherWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val weatherRepository: WeatherRepository,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val location = HiWeatherStore.currentLocation.split(",")
        val userLocation = UserLocation(location[0].toDouble(), location[1].toDouble(), location[2].toBoolean())

        return when (val w = weatherRepository.getCurrentLocationWeather(userLocation).last()) {
            is State.Success -> {
                if (w.data != null) {
                    when (val f = weatherRepository.getCurrentLocationForecast(userLocation).last()) {
                        is State.Success -> {
                            if (f.data != null) {
                                Result.success()
                            } else {
                                Result.failure()
                            }
                        }
                        else -> {
                            Result.failure()
                        }
                    }
                } else {
                    Result.failure()
                }
            }
            else -> {
                Result.failure()
            }
        }
    }
}
