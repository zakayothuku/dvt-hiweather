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
package com.dvttest.hiweather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dvttest.hiweather.data.db.dao.FavoritesDao
import com.dvttest.hiweather.data.db.dao.WeatherDao
import com.dvttest.hiweather.data.db.entities.Favorite
import com.dvttest.hiweather.data.db.entities.Forecast
import com.dvttest.hiweather.data.db.entities.Weather

@Database(
    entities = [Weather::class, Forecast::class, Favorite::class],
    version = 1,
    exportSchema = true,
    autoMigrations = []
)
abstract class HiWeatherDatabase : RoomDatabase() {
    abstract val weatherDao: WeatherDao
    abstract val favoritesDao: FavoritesDao
}
