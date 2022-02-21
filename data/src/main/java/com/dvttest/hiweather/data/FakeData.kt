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
package com.dvttest.hiweather.data

import com.dvttest.hiweather.data.api.model.CurrentForecast
import com.dvttest.hiweather.data.api.model.CurrentWeather
import com.dvttest.hiweather.data.db.entities.Forecast
import com.dvttest.hiweather.data.db.entities.Weather
import com.dvttest.hiweather.data.models.UserLocation

object FakeData {
    val fakeUserLocation = UserLocation(latitude = 0.0, longitude = 0.0, refresh = false)

    val fakeWeather = Weather(
        id = null,
        weatherId = 0,
        weather = "",
        addressName = "",
        country = "",
        lat = 0.0,
        lon = 0.0,
        temp = 0.0,
        tempMin = 0.0,
        tempMax = 0.0,
        pressure = 0,
        humidity = 0,
        lastUpdate = System.currentTimeMillis()
    )

    val fakeDaoWeather = Weather(
        id = 1,
        weatherId = 0,
        weather = "",
        addressName = "",
        country = "",
        lat = 0.0,
        lon = 0.0,
        temp = 0.0,
        tempMin = 0.0,
        tempMax = 0.0,
        pressure = 0,
        humidity = 0,
        lastUpdate = System.currentTimeMillis()
    )

    val fakeCurrentForecast = CurrentForecast(
        city = CurrentForecast.City(
            coord = CurrentForecast.City.Coord(
                lat = 0.0,
                lon = 0.0
            ),
            country = "",
            id = 0,
            name = "",
            sunrise = 0,
            sunset = 0,
            timezone = 0
        ),
        cnt = 0,
        cod = "",
        days = listOf(
            CurrentForecast.Day(
                clouds = CurrentForecast.Day.Clouds(all = 0),
                dateTimestamp = 0,
                date = "",
                weather = listOf(
                    CurrentForecast.Day.Weather(
                        description = "",
                        icon = "",
                        id = 0,
                        main = ""
                    )
                )
            )
        ),
        message = 0
    )

    val fakeCurrentWeather = CurrentWeather(
        base = "",
        clouds = CurrentWeather.Clouds(all = 0),
        cod = 0,
        info = CurrentWeather.Info(
            feelsLike = 0.0,
            humidity = 0,
            pressure = 0,
            temp = 0.0,
            tempMax = 0.0,
            tempMin = 0.0
        ),
        weather = listOf(
            CurrentWeather.Weather(
                description = "",
                icon = "",
                id = 0,
                main = ""
            )
        )
    )
    val fakeForecast = Forecast(
        id = null,
        country = "",
        lat = 0.0,
        lon = 0.0,
        weather = "",
        weatherId = 0,
        weatherIcon = "",
        temp = 0.0,
        tempMin = 0.0,
        tempMax = 0.0,
        pressure = 0,
        humidity = 0,
        date = "",
        day = "Thursday"
    )
    val fakeForecast1 = Forecast(
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
    val fakeForecast2 = Forecast(
        id = 2,
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
        day = "Tuesday"
    )
    val fakeForecast3 = Forecast(
        id = 3,
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
        day = "Wednesday"
    )
    val fakeForecast4 = Forecast(
        id = 4,
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
        day = "Thursday"
    )
    val fakeForecast5 = Forecast(
        id = 5,
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
        day = "Friday"
    )
}
