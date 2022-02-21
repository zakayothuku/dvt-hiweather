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

/**
 * Class to handle api requests state
 * */
sealed class State<out T>(
    open val data: T? = null,
    open val errorMessage: String? = null
) {
    data class Loading<out T>(override val data: T?) : State<T>()
    data class Success<out T>(override val data: T?) : State<T>()
    data class Error<out T>(override val data: T?, override val errorMessage: String) : State<T>()

    companion object {
        fun <T> loading(data: T? = null) = Loading(data)
        fun <T> success(data: T? = null) = Success(data)
        fun <T> error(data: T? = null, message: String) = Error(data, message)
    }
}
