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
import extensions.getLocalProperty
import extensions.buildConfigStringField

plugins {
    kotlin(Dependencies.Plugins.Kotlin.android)
    kotlin(Dependencies.Plugins.Kotlin.kapt)
    id("k-android-library")
}

android {
    buildTypes.forEach {
        // TODO: Replace this with your own API Key
        it.buildConfigStringField(
            "OPEN_WEATHER_API_KEY",
            System.getenv("OPEN_WEATHER_API_KEY") ?: "eb997eed3b84cc5f250bb4941de50de2" // getLocalProperty("open.weather.key")
        )
    }

    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
        correctErrorTypes = true
    }
}

dependencies {

    implementation(Dependencies.DataStore.core)
    implementation(Dependencies.DataStore.datastore)

    implementation(Dependencies.Hilt.android)
    kapt(Dependencies.Hilt.compiler)

    implementation(Dependencies.Retrofit.lib)
    implementation(Dependencies.Retrofit.loggingInterceptor)
    implementation(Dependencies.Retrofit.gsonConverter)

    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.ktx)
    kapt(Dependencies.Room.compiler)

    addTestDependencies()
}
