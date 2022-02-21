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
package com.dvttest.hiweather.data.api.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CurrentWeather(
    @SerializedName("base") val base: String = "",
    @SerializedName("clouds") val clouds: Clouds = Clouds(),
    @SerializedName("cod") val cod: Int = 0,
    @SerializedName("coord") val coordinates: Coordinates = Coordinates(),
    @SerializedName("dt") val dt: Int = 0,
    @SerializedName("id") val id: Int = 0,
    @SerializedName("main") val info: Info = Info(),
    @SerializedName("name") val name: String = "",
    @SerializedName("sys") val sys: Sys = Sys(),
    @SerializedName("timezone") val timezone: Int = 0,
    @SerializedName("visibility") val visibility: Int = 0,
    @SerializedName("weather") val weather: List<Weather> = listOf(),
    @SerializedName("wind") val wind: Wind = Wind()
) {
    @Keep
    data class Clouds(
        @SerializedName("all") val all: Int = 0
    )

    @Keep
    data class Coordinates(
        @SerializedName("lat") val lat: Double = 0.0,
        @SerializedName("lon") val lon: Double = 0.0
    )

    @Keep
    data class Info(
        @SerializedName("feels_like") val feelsLike: Double = 0.0,
        @SerializedName("humidity") val humidity: Int = 0,
        @SerializedName("pressure") val pressure: Int = 0,
        @SerializedName("temp") val temp: Double = 0.0,
        @SerializedName("temp_max") val tempMax: Double = 0.0,
        @SerializedName("temp_min") val tempMin: Double = 0.0
    )

    @Keep
    data class Sys(
        @SerializedName("country") val country: String = "",
        @SerializedName("id") val id: Int = 0,
        @SerializedName("message") val message: Double = 0.0,
        @SerializedName("sunrise") val sunrise: Int = 0,
        @SerializedName("sunset") val sunset: Int = 0,
        @SerializedName("type") val type: Int = 0
    )

    @Keep
    data class Weather(
        @SerializedName("description") val description: String = "",
        @SerializedName("icon") val icon: String = "",
        @SerializedName("id") val id: Int = 0,
        @SerializedName("main") val main: String = ""
    )

    @Keep
    data class Wind(
        @SerializedName("deg") val deg: Int = 0,
        @SerializedName("speed") val speed: Double = 0.0
    )
}
