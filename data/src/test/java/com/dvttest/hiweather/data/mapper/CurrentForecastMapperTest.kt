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

import com.dvttest.hiweather.data.FakeData.fakeCurrentForecast
import com.dvttest.hiweather.data.FakeData.fakeForecast
import com.dvttest.hiweather.data.api.model.CurrentForecast
import com.dvttest.hiweather.data.db.entities.Forecast
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class CurrentForecastMapperTest {

    private val currentForecast = mockk<CurrentForecast>()
    private lateinit var mapper: CurrentForecastMapper
    private lateinit var fakeForecastList: ArrayList<Forecast>

    @Before
    fun setUp() {
        fakeForecastList = arrayListOf()
        fakeForecastList.add(fakeForecast)

        mapper = CurrentForecastMapper()
    }

    @Test
    fun `verify CurrentForecastMapper transforms CurrentForecast to Forecast`() {
        coEvery { mapper.transformToDomain(currentForecast) } returns fakeForecastList
        val forecast = mapper.transformToDomain(fakeCurrentForecast)

        assertThat(forecast).isNotEqualTo(null)
        assertThat(forecast).isEqualTo(fakeForecastList)
    }
}
