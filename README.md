# DVT Weather Test

DVT Android Test project built on MVVM architecture pattern, implementing the latest and greatest tools & libraries like Retrofit, Dagger Hilt, Coroutines, Room & Work Manager among other Jetpack Libraries.
It fetches data from [OpenWeatherMap API](https://openweathermap.org/api).

## Architecture
* A single-activity architecture, using the [Navigation Components](https://developer.android.com/guide/navigation) to manage fragment operations.
* Pattern [Model-View-ViewModel] - MVVM which facilitates a separation of development of the graphical user interface.
* [Android architecture components](https://developer.android.com/topic/libraries/architecture/) which help to keep the application robust, testable, and maintainable.

## Technologies used:
* [Retrofit](https://square.github.io/retrofit/) a REST Client for Android which makes it relatively easy to retrieve and upload JSON (or other structured data) via a REST based webservice.
* [Dagger Hilt](https://dagger.dev/hilt/) for dependency injection.
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) to store and manage UI-related data in a lifecycle conscious way.
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) to handle data in a lifecycle-aware fashion.
* [Navigation Component](https://developer.android.com/guide/navigation) to handle all navigations and also passing of data between destinations.
* [Work Manager](https://developer.android.com/topic/libraries/architecture/workmanager) to manage Android background jobs.
* [Material Design](https://material.io/develop/android/docs/getting-started/) an adaptable system of guidelines, components, and tools that support the best practices of user interface design.
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) used to manage the local storage i.e. `writing to and reading from the database`. Coroutines help in managing background threads and reduces the need for callbacks.
* [Data Binding](https://developer.android.com/topic/libraries/data-binding/) to declaratively bind UI components in layouts to data sources.
* [Room](https://developer.android.com/topic/libraries/architecture/room) persistence library which provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
* [Android KTX](https://developer.android.com/kotlin/ktx) which helps to write more concise, idiomatic Kotlin code.

## Code style

To maintain the style and quality of the code, are used the bellow static analysis tools.

| Tools  | Config file | Check command | Fix command |
|--------|------------:|---------------|-------------|
| [detekt](https://github.com/arturbosch/detekt) | [/config/detekt](https://github.com/nuhkoca/kotlin-modular-tdd-coroutines-mvvm/tree/master/config/detekt) | `./gradlew detekt` | `./gradlew detektFormat` or `./gradlew detektAll` |
| [ktlint](https://github.com/pinterest/ktlint) | - | `./gradlew ktlint` | `./gradlew ktlintFormat` |
| [spotless](https://github.com/diffplug/spotless) | [/spotless](https://github.com/nuhkoca/kotlin-modular-tdd-coroutines-mvvm/tree/master/spotless) | `./gradlew spotlessCheck` | `./gradlew spotlessApply`
| [lint](https://developer.android.com/studio/write/lint) | [each module](https://github.com/nuhkoca/kotlin-modular-tdd-coroutines-mvvm/blob/master/app/lint.xml) | `./gradlew lint` | - |


## Setup & Installation
Clone the repository.

You require the API `OPEN_WEATHER_API_KEY` from [Open Weather](https://openweathermap.org/) to get data.
If you don’t already have an account, create one in order to request an API Key.

Add the API and Base Url for [Open Weather](https://openweathermap.org/) in your `local.properties` file as shown then sync your project.

```properties
open.weather.key=YOUR_OPEN_WEATHER_API_KEY
```


You can as well install the [test APK](https://github.com/zakayothuku/dvt-hiweather/blob/main/hiweather-test.apk) provided in the root folder for testing purposes.


## Missing Features
- Adding Favorite Locations & shows their Weather Forecast.
- View Saved Favorites on map.

## LICENSE
```
Apache 2.0 License

Copyright (c) 2022 Zakayo Thuku, dvt.co.za

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
