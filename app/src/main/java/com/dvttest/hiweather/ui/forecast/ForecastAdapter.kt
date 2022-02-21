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
package com.dvttest.hiweather.ui.forecast

import com.dvttest.hiweather.R
import com.dvttest.hiweather.core.recyclerview.DataBoundAdapter
import com.dvttest.hiweather.core.recyclerview.DataBoundViewHolder
import com.dvttest.hiweather.data.db.entities.Forecast
import com.dvttest.hiweather.databinding.ItemForecastBinding

class ForecastAdapter : DataBoundAdapter<ItemForecastBinding>(R.layout.item_forecast) {

    private var mForecast = listOf<Forecast>()

    override fun getItemCount(): Int = mForecast.size

    override fun bindItem(holder: DataBoundViewHolder<ItemForecastBinding>?, position: Int, payloads: MutableList<Any>?) {
        val binding = holder?.binding!!
        val forecast = mForecast[position]
        binding.forecast = forecast
    }

    fun updateForecast(forecast: List<Forecast>) {
        mForecast = forecast
        notifyDataSetChanged()
    }
}
