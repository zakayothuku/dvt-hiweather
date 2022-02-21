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
package com.dvttest.hiweather.data.mapper

import com.dvttest.hiweather.data.api.model.CurrentWeather
import com.dvttest.hiweather.data.db.entities.Weather

class CurrentWeatherMapper : BaseMapper<CurrentWeather, Weather> {

    override fun transformToDomain(type: CurrentWeather): Weather {
        return Weather(
            weather = type.weather.firstOrNull()?.main,
            weatherId = type.weather.firstOrNull()?.id,
            addressName = type.name,
            country = type.sys.country,
            lat = type.coordinates.lat,
            lon = type.coordinates.lon,
            temp = type.info.feelsLike,
            tempMin = type.info.tempMin,
            tempMax = type.info.tempMax,
            pressure = type.info.pressure,
            humidity = type.info.humidity,
            lastUpdate = System.currentTimeMillis()
        )
    }

    override fun transformToDto(type: Weather) = CurrentWeather()
}
