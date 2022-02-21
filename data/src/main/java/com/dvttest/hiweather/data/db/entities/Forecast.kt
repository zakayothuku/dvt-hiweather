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
package com.dvttest.hiweather.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_forecast")
data class Forecast(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int? = null,
    @ColumnInfo(name = "country") var country: String? = "",
    @ColumnInfo(name = "lat") val lat: Double? = 0.0,
    @ColumnInfo(name = "lon") val lon: Double? = 0.0,
    @ColumnInfo(name = "weather") val weather: String? = "",
    @ColumnInfo(name = "weather_id") val weatherId: Int? = 0,
    @ColumnInfo(name = "weather_icon") val weatherIcon: String? = "",
    @ColumnInfo(name = "temp") val temp: Double? = 0.0,
    @ColumnInfo(name = "temp_min") val tempMin: Double? = 0.0,
    @ColumnInfo(name = "temp_max") val tempMax: Double? = 0.0,
    @ColumnInfo(name = "pressure") val pressure: Int? = 0,
    @ColumnInfo(name = "humidity") val humidity: Int? = 0,
    @ColumnInfo(name = "date") val date: String? = "",
    @ColumnInfo(name = "day") val day: String? = "",
)
