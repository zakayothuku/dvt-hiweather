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

@Entity(tableName = "current_weather")
data class Weather(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int? = null,
    @ColumnInfo(name = "weather_id") var weatherId: Int? = null,
    @ColumnInfo(name = "weather") var weather: String? = null,
    @ColumnInfo(name = "weather_icon") var weatherIcon: String? = null,
    @ColumnInfo(name = "address_name") var addressName: String? = null,
    @ColumnInfo(name = "country") var country: String? = null,
    @ColumnInfo(name = "lat") val lat: Double? = null,
    @ColumnInfo(name = "lon") val lon: Double? = null,
    @ColumnInfo(name = "temp") val temp: Double? = null,
    @ColumnInfo(name = "temp_min") val tempMin: Double? = null,
    @ColumnInfo(name = "temp_max") val tempMax: Double? = null,
    @ColumnInfo(name = "pressure") val pressure: Int? = null,
    @ColumnInfo(name = "humidity") val humidity: Int? = null,
    @ColumnInfo(name = "last_update") val lastUpdate: Long? = null,
)
