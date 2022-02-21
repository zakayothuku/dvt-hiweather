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

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvttest.hiweather.data.db.entities.Forecast
import com.dvttest.hiweather.data.db.entities.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM current_weather ORDER BY id DESC LIMIT 1")
    fun getLastUpdatedWeather(): Flow<Weather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrentLocationWeather(weather: Weather)

    @Query("DELETE FROM current_weather")
    suspend fun deleteWeatherData()

    @Query("SELECT * FROM current_forecast GROUP BY day ORDER BY id ASC")
    fun getLastUpdatedForecast(): Flow<List<Forecast>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrentLocationForecast(forecasts: List<Forecast>)

    @Query("DELETE FROM current_forecast")
    suspend fun deleteForecastData()
}
