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
package com.dvttest.hiweather.ui.favorites

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dvttest.hiweather.R
import com.dvttest.hiweather.core.base.BaseDialogFragment
import com.dvttest.hiweather.core.extensions.observe
import com.dvttest.hiweather.core.extensions.setNavigationResult
import com.dvttest.hiweather.data.db.entities.Favorite
import com.dvttest.hiweather.data.models.UserLocation
import com.dvttest.hiweather.databinding.DialogPlaceBinding
import com.dvttest.hiweather.ui.current.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritePlaceDialog : BaseDialogFragment<DialogPlaceBinding>(layoutId = R.layout.dialog_place) {

    val viewModel: WeatherViewModel by viewModels()
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private val args: FavoritePlaceDialogArgs by navArgs()
    lateinit var favorite: Favorite

    override fun onInitDataBinding() {
        val place = args.place
        viewBinding.viewModel = viewModel
        viewBinding.location = place.name
        viewBinding.address = place.address

        observe(viewModel.currentWeather) {
            favorite = place.copy(
                latitude = it?.lat!!,
                longitude = it.lon!!,
                address = place.address,
                name = place.name,
                weather = it.weather,
                weatherIcon = it.weatherIcon,
                temp = it.temp,
                currentLocation = false
            )
            favoritesViewModel.updateLocation(favorite)
        }

        if (args.isAdd) {
            viewBinding.actionAdd.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    setNavigationResult("place", favorite)
                    dismiss()
                }
            }
        } else {
            viewBinding.actionDelete.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    setNavigationResult("delete", favorite.id)
                    dismiss()
                }
            }
        }

        viewBinding.actionClose.setOnClickListener {
            dismiss()
        }

        getSelectedLocationWeather()
    }

    /**
     * Get current weather for the selected Location.
     **/
    private fun getSelectedLocationWeather() {
        val selectedLocation = UserLocation(
            latitude = args.place.latitude,
            longitude = args.place.longitude
        )
        viewModel.getCurrentLocationWeather(selectedLocation)
    }
}
