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
package com.dvttest.hiweather.ui.current

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dvttest.hiweather.data.api.State
import com.dvttest.hiweather.data.db.entities.Forecast
import com.dvttest.hiweather.data.db.entities.Weather
import com.dvttest.hiweather.domain.usecases.LocationForecastUseCase
import com.dvttest.hiweather.domain.usecases.LocationWeatherUseCase
import com.dvttest.hiweather.fakeForecast
import com.dvttest.hiweather.fakeUserLocation
import com.dvttest.hiweather.fakeWeather
import com.dvttest.hiweather.testutils.CoroutinesTestRule
import com.dvttest.hiweather.testutils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private val currentWeatherObserver = mockk<Observer<Weather?>>(relaxed = true)
    private val forecastObserver = mockk<Observer<List<Forecast>?>>(relaxed = true)

    private lateinit var locationWeatherUseCase: LocationWeatherUseCase
    private lateinit var locationForecastUseCase: LocationForecastUseCase
    private lateinit var viewModel: WeatherViewModel
    private lateinit var fakeForecastList :ArrayList<Forecast>

    @Before
    fun setUp() = coroutinesTestRule.testDispatcher.runBlockingTest {

        locationWeatherUseCase = mockk()
        coEvery { locationWeatherUseCase.invoke(any()) } returns flow {
            emit(State.loading(null))
            emit(State.success(fakeWeather))
        }

        fakeForecastList = arrayListOf()
        fakeForecastList.add(fakeForecast)
        fakeForecastList.add(fakeForecast)
        fakeForecastList.add(fakeForecast)
        fakeForecastList.add(fakeForecast)
        fakeForecastList.add(fakeForecast)

        locationForecastUseCase = mockk()
        coEvery { locationForecastUseCase.invoke(any()) } returns flow {
            emit(State.loading(null))
            emit(State.success(fakeForecastList))
        }

        viewModel = WeatherViewModel(
            locationWeatherUseCase,
            locationForecastUseCase
        )
    }

    @Test
    fun `assert getCurrentLocationWeather returns location weather from repository successfully`() {
        viewModel.currentWeather.observeForever(currentWeatherObserver)
        viewModel.getCurrentLocationWeather(fakeUserLocation)

        coVerify(exactly = 1) { locationWeatherUseCase.invoke(fakeUserLocation) }

        assertThat(viewModel.currentWeather.getOrAwaitValue()).isEqualTo(fakeWeather)
        assertThat(viewModel.loading.getOrAwaitValue()).isEqualTo(false)
    }

    @Test
    fun `assert getCurrentLocationForecast returns location forecast list of 5 days from repository`() {
        viewModel.forecast.observeForever(forecastObserver)
        viewModel.getCurrentLocationForecast(fakeUserLocation)

        coVerify(exactly = 1) { locationForecastUseCase.invoke(fakeUserLocation) }

        assertThat(viewModel.forecast.getOrAwaitValue()).isEqualTo(fakeForecastList)
        assertThat(viewModel.forecast.value?.size).isEqualTo(5)
        assertThat(viewModel.loading.getOrAwaitValue()).isEqualTo(false)
    }

    @After
    fun tearDown() {
        viewModel.currentWeather.removeObserver(currentWeatherObserver)
        viewModel.forecast.removeObserver(forecastObserver)
    }
}
