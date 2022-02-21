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
    id(Dependencies.Plugins.Android.application)
    kotlin(Dependencies.Plugins.Kotlin.android)
    kotlin(Dependencies.Plugins.Kotlin.kapt)
    id(Dependencies.Plugins.Kotlin.parcelize)
    id(Dependencies.Plugins.safeArgsPlugin)
    id(Dependencies.Plugins.hiltAndroidPlugin)
}

android {

    compileSdk = AppInfo.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = AppInfo.APPLICATION_ID
        minSdk = AppInfo.MIN_SDK_VERSION
        targetSdk = AppInfo.TARGET_SDK_VERSION
        versionCode = AppInfo.VERSION_CODE
        versionName = AppInfo.VERSION_NAME

        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
        unitTests.all {
            if (it.name === "testDebugUnitTest") {
                it.extensions.configure(kotlinx.kover.api.KoverTaskExtension::class) {
                    isDisabled = false
                    binaryReportFile.set(file("$buildDir/custom/debug-report.bin"))
                    includes = listOf("com.dvttest.*")
                    excludes = listOf("com.dvttest.hiweather.testutils.*")
                }
                animationsDisabled = true
            }
        }
    }

    lint {
        lintConfig = file(".configs/lint.xml")
        abortOnError = false
        warningsAsErrors = true
        checkDependencies = true
        ignoreTestSources = true
        disable += "GradleDeprecated"
        disable += "OldTargetApi"
        disable += "GradleDependency"
    }

    packagingOptions {
        resources.excludes.add("**/attach_hotspot_windows.dll")
        resources.excludes.add("META-INF/licenses/**")
        resources.excludes.add("META-INF/LICENSE.md")
        resources.excludes.add("META-INF/NOTICE.md")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }

    // Use the resolution strategy along with the version in the app.gradle. It will fix the issue.
    configurations.all {
        resolutionStrategy.eachDependency {
            if ("org.jacoco" == requested.group) {
                useVersion("0.8.7")
            }
        }
    }
}

dependencies {

    implementation(project(Modules.CORE))
    implementation(project(Modules.DATA))
    implementation(project(Modules.DOMAIN))

    implementation(Dependencies.kotlin)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.material)
    implementation(Dependencies.splashscreen)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.swipeRefreshLayout)
    implementation(Dependencies.recyclerView)
    implementation(Dependencies.fragmentKtx)
    implementation(Dependencies.workManager)

    implementation(Dependencies.Navigation.fragment)
    implementation(Dependencies.Navigation.ui)

    implementation(Dependencies.Lifecycle.viewModel)
    implementation(Dependencies.Lifecycle.livedata)
    kapt(Dependencies.Lifecycle.compiler)

    implementation(Dependencies.Hilt.android)
    implementation(Dependencies.Hilt.navigation)
    implementation(Dependencies.Hilt.work)
    kapt(Dependencies.Hilt.compiler)

    implementation(Dependencies.Retrofit.gsonConverter)

    implementation(Dependencies.Coroutines.core)
    implementation(Dependencies.Coroutines.android)

    implementation(Dependencies.Play.location)

    implementation(Dependencies.timber)

    addTestDependencies()
}
