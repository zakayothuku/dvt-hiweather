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
package com.dvttest.hiweather.data.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.dvttest.hiweather.data.FakeData
import com.dvttest.hiweather.data.db.HiWeatherDatabase
import com.dvttest.hiweather.data.db.entities.Favorite
import com.dvttest.hiweather.testutils.CoroutinesTestRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@LargeTest
@ExperimentalCoroutinesApi
class FavoritesDaoTest {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var database: HiWeatherDatabase
    private lateinit var favoritesDao: FavoritesDao
    private lateinit var fakeFavoriteLocationsList : ArrayList<Favorite>

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            HiWeatherDatabase::class.java
        ).allowMainThreadQueries().build()

        fakeFavoriteLocationsList = arrayListOf()
        fakeFavoriteLocationsList.add(FakeData.fakeFavoriteLocation1)
        fakeFavoriteLocationsList.add(FakeData.fakeFavoriteLocation2)
        fakeFavoriteLocationsList.add(FakeData.fakeFavoriteLocation3)

        favoritesDao = database.favoritesDao
    }

    @Test
    fun verifyEmptyFavoritesLocationsOnInitialFetch() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // Fetch DB first time returns empty list
        val favorites = favoritesDao.getLocations().first()
        Truth.assertThat(favorites).isEqualTo(listOf<Favorite>())
    }

    @Test
    fun verifyFavoriteLocationNotEmptyAfterInsert() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // After Insert BD should return favorite locations list
        favoritesDao.saveLocation(FakeData.fakeFavoriteLocation1)
        val favorites = favoritesDao.getLocations().first()

        // Confirm inserted location is not empty and is My Current Location
        Truth.assertThat(favorites).isNotNull()
        Truth.assertThat(favorites.size).isEqualTo(1)
        Truth.assertThat(favorites[0].currentLocation).isEqualTo(true)
    }

    @Test
    fun verifyUpdateCorrectFavoriteLocations() = coroutinesTestRule.testDispatcher.runBlockingTest {
        favoritesDao.saveLocation(FakeData.fakeFavoriteLocation1)
        favoritesDao.updateLocation(FakeData.fakeFavoriteLocation1Update)
        val favorites = favoritesDao.getLocations().first()

        Truth.assertThat(favorites.first()).isEqualTo(FakeData.fakeFavoriteLocation1Update)
        Truth.assertThat(favorites.first().weather).isEqualTo("Sunny")
        Truth.assertThat(favorites.first().address).isEqualTo("Test2")
        Truth.assertThat(favorites.first().temp).isEqualTo(20.0)
    }

    @Test
    fun verifyDeleteFavoriteLocationsByIdReturnsEmptyList() = coroutinesTestRule.testDispatcher.runBlockingTest {
        favoritesDao.saveLocation(FakeData.fakeFavoriteLocation1)
        favoritesDao.deleteLocation(FakeData.fakeFavoriteLocation1.id!!)
        val favorites = favoritesDao.getLocations().first()

        Truth.assertThat(favorites).isEqualTo(listOf<Favorite>())
    }

    @Test
    fun verifyDeleteFavoriteLocationIfCurrentReturnsEmptyList() = coroutinesTestRule.testDispatcher.runBlockingTest {
        favoritesDao.saveLocation(FakeData.fakeFavoriteLocation1)
        favoritesDao.deleteLocation(FakeData.fakeFavoriteLocation1.currentLocation)
        val favorites = favoritesDao.getLocations().first()

        Truth.assertThat(favorites).isEqualTo(listOf<Favorite>())
    }

    @Test
    fun verifyDeleteAllFavoriteLocationsReturnsEmptyList() = coroutinesTestRule.testDispatcher.runBlockingTest {
        favoritesDao.saveLocation(FakeData.fakeFavoriteLocation1)
        favoritesDao.saveLocation(FakeData.fakeFavoriteLocation2)
        favoritesDao.saveLocation(FakeData.fakeFavoriteLocation3)
        favoritesDao.deleteLocations()
        val favorites = favoritesDao.getLocations().first()

        Truth.assertThat(favorites).isEqualTo(listOf<Favorite>())
    }
}
