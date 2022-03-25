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
package com.dvttest.hiweather.ui.map

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dvttest.hiweather.R
import com.dvttest.hiweather.core.base.BaseFragment
import com.dvttest.hiweather.data.db.entities.Favorite
import com.dvttest.hiweather.databinding.FragmentViewMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ViewMapFragment : BaseFragment<FragmentViewMapBinding>(layoutId = R.layout.fragment_view_map) {

    private val args: ViewMapFragmentArgs by navArgs()
    private lateinit var mGoogleMap: GoogleMap

    override fun onInitDataBinding() {
        viewBinding.actionClose.setOnClickListener {
            findNavController().navigateUp()
        }

        lifecycleScope.launchWhenCreated {
            mGoogleMap = (childFragmentManager.findFragmentById(R.id.view_map) as SupportMapFragment).awaitMap()

            args.favoriteLocations.forEach {
                mGoogleMap.addMarker {
                    title("${it.name} - ${it.address} (${it.temp}) °C")
                    position(LatLng(it.latitude, it.longitude))
                }
            }

            val currentLocation = args.favoriteLocations.find { it.currentLocation } ?: Favorite()
            mGoogleMap.moveCamera(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition(
                        LatLng(
                            currentLocation.latitude,
                            currentLocation.longitude
                        ),
                        3F,
                        0F,
                        0F
                    )
                )
            )

            mGoogleMap.setOnMarkerClickListener(onMapMarkerClicked)
        }
    }

    private val onMapMarkerClicked = GoogleMap.OnMarkerClickListener {
        false
    }
}
