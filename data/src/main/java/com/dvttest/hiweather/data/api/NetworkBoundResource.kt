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

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * A generic class that can provide a resource backed by both the room database and the network.
 *
 * We can read more about it in the [Architecture Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
 **/
object NetworkBoundResource {
    inline fun <ResultType, RequestType> networkBoundResource(
        crossinline query: () -> Flow<ResultType>,
        crossinline fetch: suspend () -> RequestType,
        crossinline saveFetchResponse: suspend (RequestType) -> Unit,
        crossinline shouldFetch: (ResultType) -> Boolean = { true },
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) = flow {

        val data = query().first()
        val flow = if (shouldFetch(data)) {
            emit(State.loading(data))
            try {
                saveFetchResponse(fetch())
                query().map { State.success(it) }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                query().map { State.error(it, throwable.message.toString()) }
            }
        } else {
            query().map { State.success(it) }
        }

        emitAll(flow)

    }.flowOn(coroutineDispatcher)
}
