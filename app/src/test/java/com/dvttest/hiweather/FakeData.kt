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

import com.dvttest.hiweather.data.db.entities.Forecast
import com.dvttest.hiweather.data.db.entities.Weather
import com.dvttest.hiweather.data.models.UserLocation

val fakeUserLocation = UserLocation(latitude = 0.0, longitude = 0.0)

val fakeWeather = Weather(
    id = 1,
    weatherId = 200,
    weather = "Sunny",
    addressName = "Kikuyu",
    country = "Kenya",
    lat = 1.223392,
    lon = -0.442221,
    temp = 20.0,
    tempMin = 18.0,
    tempMax = 23.0,
    pressure = 1002,
    humidity = 330,
    lastUpdate = 292393910
)

val fakeForecast = Forecast(
    id = 1,
    country = "Kenya",
    lat = null,
    lon = null,
    weather = "Cloudy",
    weatherId = 300,
    weatherIcon = "10d",
    temp = 23.1,
    tempMin = 17.4,
    tempMax = 23.9,
    pressure = 202,
    humidity = 504,
    date = "2022-02-01 09:04:22",
    day = "Monday"
)
