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
package com.dvttest.hiweather

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import com.dvttest.hiweather.data.datastore.HiWeatherStore
import com.dvttest.hiweather.data.respositories.FavoritesRepository
import com.dvttest.hiweather.data.respositories.WeatherRepository
import com.dvttest.hiweather.worker.HiWeatherWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class HiWeather : Application(), Configuration.Provider {

    @Inject
    lateinit var weatherRepository: WeatherRepository

    @Inject
    lateinit var favoritesRepository: FavoritesRepository

    override fun onCreate() {
        super.onCreate()
        HiWeatherStore.init(applicationContext)
        initTimberDebugTree()
    }

    /** Initialize Timber debug tree. */
    private fun initTimberDebugTree() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun getWorkManagerConfiguration(): Configuration {
        val workerFactory = DelegatingWorkerFactory()
        workerFactory.addFactory(HiWeatherWorkerFactory(weatherRepository, favoritesRepository))

        return Configuration.Builder()
            .setMinimumLoggingLevel(Log.INFO)
            .setWorkerFactory(workerFactory)
            .build()
    }
}
