/**
 * Project dependencies, makes it easy to include external binaries or
 * other library modules to build.
 */
object Dependencies {

    const val kotlinVersion = "1.6.10"
    const val daggerVersion = "2.40.5"

    object Plugins {

        object Kotlin {

            const val android = "android"
            const val parcelize = "kotlin-parcelize"
            const val kapt = "kapt"
        }

        object Android {

            const val application = "com.android.application"
            const val androidLibrary = "com.android.library"
            const val dynamicFeatures = "com.android.dynamic-feature"
        }

        const val safeArgsPlugin = "androidx.navigation.safeargs.kotlin"
        const val hiltAndroidPlugin = "dagger.hilt.android.plugin"
        const val secretsPlugin = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin"
    }

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"

    const val coreKtx = "androidx.core:core-ktx:1.7.0"
    const val splashscreen = "androidx.core:core-splashscreen:1.0.0-beta01"
    const val appCompat = "androidx.appcompat:appcompat:1.4.1"
    const val material = "com.google.android.material:material:1.4.0"
    const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:1.4.1"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.3"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
    const val workManager = "androidx.work:work-runtime-ktx:2.7.1"

    const val lottie = "com.airbnb.android:lottie:4.2.2"
    const val timber = "com.jakewharton.timber:timber:4.7.1"

    object Navigation {

        private const val version = "2.4.0"
        const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
        const val ui = "androidx.navigation:navigation-ui-ktx:$version"
        const val runtime = "androidx.navigation:navigation-runtime-ktx:$version"
    }

    object Lifecycle {

        private const val version = "2.3.1"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
        const val savedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$version"
        const val process = "androidx.lifecycle:lifecycle-process:$version"

        // kapt
        const val compiler = "androidx.lifecycle:lifecycle-compiler:$version"
    }

    object Hilt {

        private const val version = "1.0.0"
        const val android = "com.google.dagger:hilt-android:$daggerVersion"
        const val navigation = "androidx.hilt:hilt-navigation-fragment:$version"
        const val work = "androidx.hilt:hilt-work:$version"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:$version"

        // kapt
        const val compiler = "com.google.dagger:hilt-android-compiler:$daggerVersion"
    }

    object Retrofit {

        const val lib = "com.squareup.retrofit2:retrofit:2.9.0"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:2.9.0"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.3"
    }

    object Coroutines {

        private const val version = "1.5.0"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val playServices = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$version"
    }

    object DataStore {
        private const val version = "1.0.0"
        const val core = "androidx.datastore:datastore-core:$version"
        const val datastore = "androidx.datastore:datastore-preferences:$version"
    }

    object Room {

        private const val version = "2.4.1"
        const val runtime = "androidx.room:room-runtime:$version"
        const val ktx = "androidx.room:room-ktx:$version"

        // kapt
        const val compiler = "androidx.room:room-compiler:$version"
    }

    object Play {

        const val location = "com.google.android.gms:play-services-location:19.0.1"
        const val maps = "com.google.maps.android:maps-ktx:3.2.1"
        const val places = "com.google.maps.android:places-ktx:1.0.0"
    }

    object Glide {
        private const val version = "4.12.0"
        const val core = "com.github.bumptech.glide:glide:$version"

        // kapt
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object TestAndroidDependencies {
        object Espresso {
            const val version = "3.3.0"
            const val espresso = "androidx.test.espresso:espresso-core:$version"
            const val espressoContrib = "androidx.test.espresso:espresso-contrib:$version"
            const val espressoIntent = "androidx.test.espresso:espresso-intents:$version"
            const val idlingResource = "androidx.test.espresso:espresso-idling-resource:$version"
        }

        const val hiltTesting = "com.google.dagger:hilt-android-testing:2.37"
    }

    object TestDependencies {
        const val junit = "androidx.test.ext:junit:1.1.3-beta02"
        const val junit5Api = "org.junit.jupiter:junit-jupiter-api:5.7.2"
        const val junit5Engine = "org.junit.jupiter:junit-jupiter-engine:5.7.2"

        const val testCore = "androidx.test:core-ktx:1.4.0"
        const val testExt = "androidx.test.ext:junit-ktx:1.1.3"
        const val testRunner = "androidx.test:runner:1.4.0"
        const val testRules = "androidx.test:rules:1.4.0"

        const val mockk = "io.mockk:mockk:1.11.0"
        const val truth = "com.google.truth:truth:1.1.3"

        const val roomTest = "androidx.room:room-testing:2.3.0"
        const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0"
        const val workTest = "androidx.work:work-testing:2.7.1"
        const val archTesting = "androidx.arch.core:core-testing:2.1.0"
        const val fragmentTest = "androidx.fragment:fragment-testing:1.4.0-alpha09"
        const val mockWebServer = "com.squareup.okhttp3:mockwebserver:4.9.3"
    }
}
