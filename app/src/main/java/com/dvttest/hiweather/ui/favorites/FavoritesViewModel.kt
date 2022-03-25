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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvttest.hiweather.data.db.entities.Favorite
import com.dvttest.hiweather.domain.usecases.DeleteLocationByIdUseCase
import com.dvttest.hiweather.domain.usecases.GetLocationsUseCase
import com.dvttest.hiweather.domain.usecases.SaveLocationUseCase
import com.dvttest.hiweather.domain.usecases.UpdateLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val saveLocationUseCase: SaveLocationUseCase,
    private val getLocationsUseCase: GetLocationsUseCase,
    private val updateLocationUseCase: UpdateLocationUseCase,
    private val deleteLocationByIdUseCase: DeleteLocationByIdUseCase
) : ViewModel() {

    suspend fun getLocations(): Flow<List<Favorite>> {
        return getLocationsUseCase.invoke(Any())
    }

    fun saveLocation(favorite: Favorite) {
        viewModelScope.launch {
            saveLocationUseCase.invoke(favorite)
        }
    }

    fun updateLocation(favorite: Favorite) {
        viewModelScope.launch {
            updateLocationUseCase.invoke(favorite)
        }
    }

    fun deleteLocation(id: Int) {
        viewModelScope.launch {
            deleteLocationByIdUseCase.invoke(id)
        }
    }
}
