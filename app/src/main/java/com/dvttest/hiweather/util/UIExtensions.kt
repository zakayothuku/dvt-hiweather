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
package com.dvttest.hiweather.util

import android.annotation.SuppressLint
import android.text.format.DateUtils
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dvttest.hiweather.R
import com.dvttest.hiweather.core.bindings.imageName
import com.dvttest.hiweather.core.bindings.imageUrl
import com.dvttest.hiweather.data.db.entities.Forecast
import com.dvttest.hiweather.data.models.WeatherType
import com.dvttest.hiweather.ui.forecast.ForecastAdapter
import com.google.android.material.appbar.CollapsingToolbarLayout


object UIExtensions {

    @JvmStatic
    @BindingAdapter("temperature")
    fun TextView.setTemperature(double: Double?) {
        val context = this.context
        if (double != null) {
            this.text = context.resources.getString(R.string.current_temperature, double.toInt().toString())
        } else {
            this.text = context.resources.getString(R.string.current_temperature, "--")
        }
    }

    @JvmStatic
    @BindingAdapter("weatherImage")
    fun ImageView.setWeatherImage(code: Int) {
        when (code) {
            in WeatherType.CLEAR.range -> {
                this.imageName("bg_forest_sunny")
            }
            in WeatherType.CLOUDS.range,
            in WeatherType.ATMOSPHERE.range -> {
                this.imageName("bg_forest_cloudy")
            }
            in WeatherType.DRIZZLE.range,
            in WeatherType.RAIN.range,
            in WeatherType.THUNDERSTORM.range,
            in WeatherType.SNOW.range -> {
                this.imageName("bg_forest_rainy")
            }
        }
    }

    @JvmStatic
    @BindingAdapter("weatherBackground")
    fun ViewGroup.setWeatherBackgroundColor(code: Int) {
        when (code) {
            in WeatherType.CLEAR.range -> {
                this.setBackgroundResource(com.dvttest.hiweather.core.R.color.color_sunny)
            }
            in WeatherType.CLOUDS.range,
            in WeatherType.ATMOSPHERE.range -> {
                this.setBackgroundResource(com.dvttest.hiweather.core.R.color.color_cloudy)
            }
            in WeatherType.DRIZZLE.range,
            in WeatherType.RAIN.range,
            in WeatherType.THUNDERSTORM.range,
            in WeatherType.SNOW.range -> {
                this.setBackgroundResource(com.dvttest.hiweather.core.R.color.color_rainy)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("weatherScrim")
    fun CollapsingToolbarLayout.setWeatherScrimColor(code: Int) {
        when (code) {
            in WeatherType.CLEAR.range -> {
                this.setContentScrimColor(ContextCompat.getColor(context, com.dvttest.hiweather.core.R.color.color_sunny))
                this.setStatusBarScrimColor(ContextCompat.getColor(context, com.dvttest.hiweather.core.R.color.color_sunny))
            }
            in WeatherType.CLOUDS.range,
            in WeatherType.ATMOSPHERE.range -> {
                this.setContentScrimColor(ContextCompat.getColor(context, com.dvttest.hiweather.core.R.color.color_cloudy))
                this.setStatusBarScrimColor(ContextCompat.getColor(context, com.dvttest.hiweather.core.R.color.color_cloudy))
            }
            in WeatherType.DRIZZLE.range,
            in WeatherType.RAIN.range,
            in WeatherType.THUNDERSTORM.range,
            in WeatherType.SNOW.range -> {
                this.setContentScrimColor(ContextCompat.getColor(context, com.dvttest.hiweather.core.R.color.color_rainy))
                this.setStatusBarScrimColor(ContextCompat.getColor(context, com.dvttest.hiweather.core.R.color.color_rainy))
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("lastUpdate")
    fun TextView.setLastUpdate(lastUpdate: Long) {
        val now = System.currentTimeMillis()
        val ago = DateUtils.getRelativeTimeSpanString(lastUpdate, now, 0L, DateUtils.FORMAT_ABBREV_RELATIVE)
        this.text = "Last update: $ago"
    }

    @JvmStatic
    @BindingAdapter("forecast")
    fun RecyclerView.updateForecast(forecasts: List<Forecast>?) {
        if (forecasts != null) {
            (adapter as ForecastAdapter).updateForecast(forecasts)
        }
    }

    @JvmStatic
    @BindingAdapter("forecastIcon")
    fun ImageView.setForecastIcon(code: String) {
        val iconUrl = "http://openweathermap.org/img/wn/$code@2x.png"
        this.imageUrl(iconUrl)
    }
}
