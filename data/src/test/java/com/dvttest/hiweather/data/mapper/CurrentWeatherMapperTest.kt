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

import com.dvttest.hiweather.data.FakeData
import com.dvttest.hiweather.data.FakeData.fakeWeather
import com.dvttest.hiweather.data.api.model.CurrentWeather
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class CurrentWeatherMapperTest {

    private val currentWeather = mockk<CurrentWeather>()
    private lateinit var mapper: CurrentWeatherMapper

    @Before
    fun setUp() {
        mapper = CurrentWeatherMapper()
    }

    @Test
    fun `verify CurrentWeatherMapper transforms CurrentWeather to Weather`() {
        coEvery { mapper.transformToDomain(currentWeather) } returns fakeWeather
        val forecast = mapper.transformToDomain(FakeData.fakeCurrentWeather)

        assertThat(forecast).isNotEqualTo(null)
        assertThat(forecast.weather).isEqualTo(fakeWeather.weather)
        assertThat(forecast.weatherId).isEqualTo(fakeWeather.weatherId)
        assertThat(forecast.addressName).isEqualTo(fakeWeather.addressName)
        assertThat(forecast.country).isEqualTo(fakeWeather.country)
        assertThat(forecast.lat).isEqualTo(fakeWeather.lat)
        assertThat(forecast.lon).isEqualTo(fakeWeather.lon)
        assertThat(forecast.temp).isEqualTo(fakeWeather.temp)
        assertThat(forecast.tempMin).isEqualTo(fakeWeather.temp)
        assertThat(forecast.tempMax).isEqualTo(fakeWeather.tempMax)
        assertThat(forecast.pressure).isEqualTo(fakeWeather.pressure)
        assertThat(forecast.humidity).isEqualTo(fakeWeather.humidity)
    }
}
