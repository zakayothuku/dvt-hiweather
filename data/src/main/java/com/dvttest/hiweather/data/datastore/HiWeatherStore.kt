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
package com.dvttest.hiweather.data.datastore

import android.content.Context

object HiWeatherStore {

    private lateinit var dataStore: DataStoreUtils

    fun init(context: Context) {
        dataStore = DataStoreUtils
        dataStore.init(context)
    }

    private const val CURRENT_LOCATION = "current_location"

    var currentLocation: String
        get() = dataStore.getSyncData(CURRENT_LOCATION, "")
        set(s) {
            dataStore.putSyncData(CURRENT_LOCATION, s)
        }
}
