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

import com.dvttest.hiweather.data.api.State
import com.dvttest.hiweather.data.db.entities.Forecast
import com.dvttest.hiweather.data.db.entities.Weather
import com.dvttest.hiweather.data.models.UserLocation
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getCurrentLocationWeather(userLocation: UserLocation): Flow<State<Weather?>>
    fun getCurrentLocationForecast(userLocation: UserLocation): Flow<State<List<Forecast>?>>
}
