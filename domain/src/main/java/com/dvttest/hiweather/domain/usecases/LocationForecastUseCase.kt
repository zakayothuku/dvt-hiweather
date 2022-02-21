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
package com.dvttest.hiweather.domain.usecases

import com.dvttest.hiweather.data.api.State
import com.dvttest.hiweather.data.db.entities.Forecast
import com.dvttest.hiweather.data.models.UserLocation
import com.dvttest.hiweather.data.respositories.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias LocationForecastBaseUseCase = BaseUseCase<UserLocation, Flow<State<List<Forecast>?>>>

class LocationForecastUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) : LocationForecastBaseUseCase {

    override suspend fun invoke(params: UserLocation) = weatherRepository.getCurrentLocationForecast(params)
}
