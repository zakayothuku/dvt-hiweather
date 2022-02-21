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
package com.dvttest.hiweather.data.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.LargeTest
import com.dvttest.hiweather.data.FakeData.fakeDaoWeather
import com.dvttest.hiweather.data.FakeData.fakeForecast
import com.dvttest.hiweather.data.FakeData.fakeForecast1
import com.dvttest.hiweather.data.FakeData.fakeForecast2
import com.dvttest.hiweather.data.FakeData.fakeForecast3
import com.dvttest.hiweather.data.FakeData.fakeForecast4
import com.dvttest.hiweather.data.FakeData.fakeForecast5
import com.dvttest.hiweather.data.FakeData.fakeWeather
import com.dvttest.hiweather.data.db.HiWeatherDatabase
import com.dvttest.hiweather.data.db.entities.Forecast
import com.dvttest.hiweather.testutils.CoroutinesTestRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@LargeTest
@ExperimentalCoroutinesApi
class WeatherDaoTest {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var database: HiWeatherDatabase
    private lateinit var weatherDao: WeatherDao
    private lateinit var fakeForecastList : ArrayList<Forecast>

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            HiWeatherDatabase::class.java
        ).allowMainThreadQueries().build()

        fakeForecastList = arrayListOf()
        fakeForecastList.add(fakeForecast1)
        fakeForecastList.add(fakeForecast2)
        fakeForecastList.add(fakeForecast3)
        fakeForecastList.add(fakeForecast4)
        fakeForecastList.add(fakeForecast5)

        weatherDao = database.weatherDao
    }

    @Test
    fun verifyNullWeatherDataOnInitialFetch() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // Fetch DB first time returns null
        val weather = weatherDao.getLastUpdatedWeather().first()
        assertThat(weather).isEqualTo(null)
    }

    @Test
    fun verifyWeatherDataNotNullAfterInsert() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // After Insert BD should return weather data
        weatherDao.saveCurrentLocationWeather(fakeDaoWeather)
        val weather = weatherDao.getLastUpdatedWeather().first()

        assertThat(weather).isNotNull()
        assertThat(weather.id).isEqualTo(fakeDaoWeather.id)
        assertThat(weather.weatherId).isEqualTo(fakeDaoWeather.weatherId)
        assertThat(weather.weather).isEqualTo(fakeDaoWeather.weather)
        assertThat(weather.temp).isEqualTo(fakeDaoWeather.temp)
        assertThat(weather.tempMin).isEqualTo(fakeDaoWeather.tempMin)
        assertThat(weather.tempMax).isEqualTo(fakeDaoWeather.tempMax)
        assertThat(weather.humidity).isEqualTo(fakeDaoWeather.humidity)
        assertThat(weather.pressure).isEqualTo(fakeDaoWeather.pressure)
        assertThat(weather.lat).isEqualTo(fakeDaoWeather.lat)
        assertThat(weather.lon).isEqualTo(fakeDaoWeather.lon)
        assertThat(weather.country).isEqualTo(fakeDaoWeather.country)
        assertThat(weather.addressName).isEqualTo(fakeDaoWeather.addressName)
        assertThat(weather.lastUpdate).isEqualTo(fakeDaoWeather.lastUpdate)
    }

    @Test
    fun verifyDeleteWeatherDataReturnsNull() = coroutinesTestRule.testDispatcher.runBlockingTest {
        weatherDao.saveCurrentLocationWeather(fakeDaoWeather)
        weatherDao.deleteWeatherData()
        val weather = weatherDao.getLastUpdatedWeather().first()
        assertThat(weather).isEqualTo(null)
    }

    @Test
    fun verifyEmptyForecastListOnInitialFetch() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val forecast = weatherDao.getLastUpdatedForecast().first()
        assertThat(forecast).isEqualTo(listOf<Forecast>())
    }

    @Test
    fun verifyForecastListNotEmptyAfterInsert() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // After Insert BD should return weather data
        weatherDao.saveCurrentLocationForecast(fakeForecastList)
        val forecasts = weatherDao.getLastUpdatedForecast().first()
        assertThat(forecasts).isNotNull()
        assertThat(forecasts.count()).isNotEqualTo(0)
        // for (index in forecasts.indices) {
        //     assertThat(forecasts[index].id).isEqualTo(fakeForecastList[index-1].id)
        //     assertThat(forecasts[index].country).isEqualTo(fakeForecastList[index-1].country)
        //     assertThat(forecasts[index].lat).isEqualTo(fakeForecastList[index-1].lat)
        //     assertThat(forecasts[index].lon).isEqualTo(fakeForecastList[index-1].lon)
        //     assertThat(forecasts[index].weather).isEqualTo(fakeForecastList[index-1].weather)
        //     assertThat(forecasts[index].weatherId).isEqualTo(fakeForecastList[index-1].weatherId)
        //     assertThat(forecasts[index].weatherIcon).isEqualTo(fakeForecastList[index-1].weatherIcon)
        //     assertThat(forecasts[index].temp).isEqualTo(fakeForecastList[index-1].temp)
        //     assertThat(forecasts[index].tempMin).isEqualTo(fakeForecastList[index-1].tempMin)
        //     assertThat(forecasts[index].tempMax).isEqualTo(fakeForecastList[index-1].tempMax)
        //     assertThat(forecasts[index].pressure).isEqualTo(fakeForecastList[index-1].pressure)
        //     assertThat(forecasts[index].humidity).isEqualTo(fakeForecastList[index-1].humidity)
        //     assertThat(forecasts[index].date).isEqualTo(fakeForecastList[index-1].date)
        //     assertThat(forecasts[index].day).isEqualTo(fakeForecastList[index-1].day)
        // }
    }

    @Test
    fun verifyDeleteForecastDataReturnsEmptyList() = coroutinesTestRule.testDispatcher.runBlockingTest {
        weatherDao.saveCurrentLocationForecast(fakeForecastList)
        weatherDao.deleteForecastData()
        val forecast = weatherDao.getLastUpdatedForecast().first()
        assertThat(forecast).isEqualTo(listOf<Forecast>())
    }

    @After
    fun tearDown() {
        database.close()
    }
}
