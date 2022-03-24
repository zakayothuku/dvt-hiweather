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

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dvttest.hiweather.BuildConfig
import com.dvttest.hiweather.R
import com.dvttest.hiweather.core.base.BaseFragment
import com.dvttest.hiweather.core.extensions.getNavigationResult
import com.dvttest.hiweather.core.recyclerview.GridSpacingItemDecoration
import com.dvttest.hiweather.core.recyclerview.RecyclerViewClickListener
import com.dvttest.hiweather.data.api.State
import com.dvttest.hiweather.data.db.entities.Favorite
import com.dvttest.hiweather.data.models.UserLocation
import com.dvttest.hiweather.databinding.FragmentFavoritesBinding
import com.dvttest.hiweather.ui.current.WeatherViewModel
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(
    layoutId = R.layout.fragment_favorites
), RecyclerViewClickListener {

    private val viewModel: FavoritesViewModel by viewModels()
    private val weatherViewModel: WeatherViewModel by viewModels()
    private val favoritesAdapter by lazy { FavoritesAdapter(this) }

    lateinit var favoriteLocations: ArrayList<Favorite>

    companion object {
        private const val AUTOCOMPLETE_REQUEST_CODE = 100
    }

    override fun onInitDataBinding() {
        setupViews()
        loadFavorites()
    }

    private fun setupViews() {
        viewBinding.toolbar.apply {
            setupWithNavController(findNavController())
            inflateMenu(R.menu.menu_favorites)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.choose_city -> {
                        setupPlacesAutocomplete()
                    }
                    R.id.view_map -> {
                        val directions = FavoritesFragmentDirections
                            .actionFavoritesToViewMap(favoriteLocations.toTypedArray())
                        findNavController().navigate(directions)
                    }
                }
                super.onOptionsItemSelected(menuItem)
            }
        }
        viewBinding.favoritesList.apply {
            adapter = favoritesAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            addItemDecoration(
                GridSpacingItemDecoration(
                    requireContext(),
                    spanCount = 2,
                    spacing = com.dvttest.hiweather.core.R.dimen.keyline_4,
                    includeEdge = true,
                    headerNum = 0
                )
            )
        }
    }

    private fun loadFavorites() {
        lifecycleScope.launch {
            try {
                viewModel.getLocations().collect {
                    favoriteLocations = it as ArrayList<Favorite>
                    favoritesAdapter.updateFavorites(it)
                    // updateWeatherFavorites(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun updateWeatherFavorites(favorites: List<Favorite>) {
        lifecycleScope.launch {
            try {
                favorites.forEachIndexed { index, favorite ->
                    weatherViewModel.getFavoriteLocationWeather(
                        UserLocation(
                            latitude = favorite.latitude,
                            longitude = favorite.longitude
                        )
                    ).collect { state ->
                        if (state is State.Success) {
                            val updatedFavorite = favorite.copy(
                                latitude = state.data?.lat!!,
                                longitude = state.data?.lon!!,
                                weather = state.data?.weather,
                                weatherIcon = state.data?.weatherIcon,
                                temp = state.data?.temp,
                            )
                            favoritesAdapter.updateFavoriteWeather(index, updatedFavorite)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNavigationResult("place", Favorite()) { place ->
            if (place.name.isNotEmpty()) {
                viewModel.saveLocation(place)
            }
        }
        getNavigationResult("delete", null) { id: Int? ->
            if (id != null) {
                viewModel.deleteLocation(id)
            }
        }
    }

    override fun onClick(data: Any?) {
        data as Favorite
        if (!data.currentLocation) {
            val directions = FavoritesFragmentDirections.actionFavoritesToPlaceDialog(isAdd = false, place = data)
            findNavController().navigate(directions)
        } else {
            findNavController().navigateUp()
        }
    }

    private fun setupPlacesAutocomplete() {
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), BuildConfig.PLACES_API_KEY)
        }
        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        val fields = listOf(Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.NAME)

        // Start the autocomplete intent.
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .setTypeFilter(TypeFilter.REGIONS)
            .build(requireContext())
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        val favorite = Favorite(
                            latitude = place.latLng?.latitude!!,
                            longitude = place.latLng?.longitude!!,
                            address = place.address!!,
                            name = place.name!!
                        )
                        val directions = FavoritesFragmentDirections.actionFavoritesToPlaceDialog(isAdd = true, place = favorite)
                        findNavController().navigate(directions)
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), getString(R.string.location_load_error), Toast.LENGTH_SHORT).show()
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
