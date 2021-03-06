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
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.dvttest.hiweather.data.respositories.FavoritesRepository
import com.dvttest.hiweather.data.respositories.WeatherRepository

class HiWeatherWorkerFactory(
    private val weatherRepository: WeatherRepository,
    private val favoritesRepository: FavoritesRepository,
) : WorkerFactory() {

    override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters): ListenableWorker? {
        return when (workerClassName) {
            UpdateWeatherWorker::class.java.name -> {
                UpdateWeatherWorker(
                    appContext,
                    workerParameters,
                    weatherRepository,
                    favoritesRepository
                )
            }
            // Return null, so that the base class can delegate to the default WorkerFactory.
            else -> null
        }
    }
}
