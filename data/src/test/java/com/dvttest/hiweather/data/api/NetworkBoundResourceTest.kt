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
package com.dvttest.hiweather.data.api

import com.dvttest.hiweather.data.FakeData.fakeCurrentWeather
import com.dvttest.hiweather.data.FakeData.fakeWeather
import com.dvttest.hiweather.data.api.NetworkBoundResource.networkBoundResource
import com.dvttest.hiweather.data.api.model.CurrentWeather
import com.dvttest.hiweather.data.db.entities.Weather
import com.dvttest.hiweather.testutils.CoroutinesTestRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NetworkBoundResourceTest {

    interface FakeNetworkBoundResourceHolder {
        fun query(): Flow<Weather>
        suspend fun fetch(): CurrentWeather
        fun saveFetchResponse(currentWeather: CurrentWeather)
        fun shouldFetch(): Boolean
    }

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @MockK
    lateinit var holder: FakeNetworkBoundResourceHolder

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        every { holder.query() } returns flow { emit(fakeWeather) }
        coEvery { holder.fetch() } returns fakeCurrentWeather
        every { holder.saveFetchResponse(any()) } just runs
    }

    @Test
    fun `resource returns correct query result`() = runBlocking {
        every { holder.shouldFetch() } returns true

        val flow = networkBoundResource(
            query = {
                holder.query()
            },
            fetch = {
                holder.fetch()
            },
            saveFetchResponse = {
                holder.saveFetchResponse(it)
            },
            shouldFetch = {
                holder.shouldFetch()
            }
        )

        val result = flow.take(7).toList().first().data
        assertThat(result).isEqualTo(fakeWeather)
    }

    @Test
    fun `resource calls correct sequence when fetching`() = runBlocking {
        every { holder.shouldFetch() } returns true
        val flow = networkBoundResource(
            query = {
                holder.query()
            },
            fetch = {
                holder.fetch()
            },
            saveFetchResponse = {
                holder.saveFetchResponse(it)
            },
            shouldFetch = {
                holder.shouldFetch()
            }
        )

        flow.take(7).toList()

        coVerifySequence {
            holder.query()
            holder.shouldFetch()
            holder.fetch()
            holder.saveFetchResponse(any())
            holder.query()
        }
    }

    @Test
    fun `resource calls correct sequence when offline`() = runBlocking {
        every { holder.shouldFetch() } returns false
        val flow = networkBoundResource(
            query = {
                holder.query()
            },
            fetch = {
                holder.fetch()
            },
            saveFetchResponse = {
                holder.saveFetchResponse(it)
            },
            shouldFetch = {
                holder.shouldFetch()
            }
        )

        flow.first()

        coVerifySequence {
            holder.query()
            holder.shouldFetch()
            holder.query()
        }
    }

}
