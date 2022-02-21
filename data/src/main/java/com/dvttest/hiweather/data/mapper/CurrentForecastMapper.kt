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

import com.dvttest.hiweather.data.api.model.CurrentForecast
import com.dvttest.hiweather.data.db.entities.Forecast
import java.text.SimpleDateFormat
import java.util.Locale

class CurrentForecastMapper : BaseMapper<CurrentForecast, List<Forecast>> {

    override fun transformToDomain(type: CurrentForecast): List<Forecast> {
        val forecasts = arrayListOf<Forecast>()
        for (forecastDay in type.days) {
            val forecast = Forecast(
                country = type.city.country,
                lat = type.city.coord.lat,
                lon = type.city.coord.lon,
                weather = forecastDay.weather[0].main,
                weatherId = forecastDay.weather[0].id,
                weatherIcon = forecastDay.weather[0].icon,
                temp = forecastDay.info.feelsLike,
                tempMin = forecastDay.info.tempMin,
                tempMax = forecastDay.info.tempMax,
                pressure = forecastDay.info.pressure,
                humidity = forecastDay.info.humidity,
                date = forecastDay.date,
                day = getDayOfWeek(forecastDay.dateTimestamp)
            )
            forecasts.add(forecast)
        }
        return forecasts
    }

    private fun getDayOfWeek(dateTimestamp: Int): String? {
        return SimpleDateFormat("EEEE", Locale.ENGLISH).format(dateTimestamp * 1000)
    }

    override fun transformToDto(type: List<Forecast>): CurrentForecast = CurrentForecast()
}
