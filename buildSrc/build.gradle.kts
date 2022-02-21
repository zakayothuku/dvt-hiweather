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
plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
    `java-gradle-plugin`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

object Plugins {
    const val gradle = "com.android.tools.build:gradle:7.1.1"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10"
    const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5"
    const val hiltAndroid = "com.google.dagger:hilt-android-gradle-plugin:2.40.5"

    const val spotless = "com.diffplug.spotless:spotless-plugin-gradle:5.15.0"
    const val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.18.1"
    const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:10.1.0"
    const val kover = "org.jetbrains.kotlinx:kover:0.5.0"
}

dependencies {
    implementation(Plugins.gradle)
    implementation(Plugins.kotlin)
    implementation(Plugins.safeArgs)
    implementation(Plugins.hiltAndroid)

    implementation(Plugins.spotless)
    implementation(Plugins.detekt)
    implementation(Plugins.ktlint)
    implementation(Plugins.kover)
}

gradlePlugin {
    plugins.register("detekt") {
        id = "detekt"
        implementationClass = "plugins.PluginDetekt"
    }
    plugins.register("spotless") {
        id = "spotless"
        implementationClass = "plugins.PluginSpotless"
    }
    plugins.register("ktlint") {
        id = "ktlint"
        implementationClass = "plugins.PluginKtlint"
    }
    plugins.register("kover") {
        id = "kover"
        implementationClass = "plugins.PluginKover"
    }
    plugins.register("k-android-library") {
        id = "k-android-library"
        implementationClass = "common.AndroidLibraryConfiguration"
    }
}
