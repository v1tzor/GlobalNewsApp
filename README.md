![](https://i.imgur.com/IElBM4P.png)

# GlobalNewsApp

A simple application for viewing news with multimodule architecture, MVVM, paging, unit tests.

## Installation

GlobalNews requires a minimum API level of 23. Clone the repository. To request news, you will need an API key, i.e. `global_news_api_key` from  [GNews](https://gnews.io/). If you don't have an account yet, you will need to create one to request an API key.

In the root directory of your project, inside the local.properties file (create it if it is not available), include the following lines:

```properties
# Get this from GNews
global_news_api_key=<insert>
global_news_base_url="https://gnews.io/api/v4/"
```

## Technologies used:

* [Kotlin](https://kotlinlang.org/) a statically typed, object-oriented programming language running on top of Java Virtual Machine and developed by JetBrains.
* [MVVM (Jetpack ViewModel + LiveData)](https://developer.android.com/topic/architecture) an architectural pattern that improves the separation of tasks, it allows you to separate the logic of the user interface from the business logic of the app.
* [Multi module]() allows you to split the application into features. A feature is a logically complete, maximally independent program module that solves a specific user problem, with clearly defined external dependencies, and which is relatively easy to reuse in another program.
* [Single Activity]() an architecture that has only one activity acting as a container for fragments
* [Paging Library](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) helps you load and display small chunks of data at a time.
* [Dagger 2](https://dagger.dev/dev-guide/) for dependency injection.
* [Retrofit](https://square.github.io/retrofit/) a REST Client for Android which makes it relatively easy to retrieve and upload JSON (or other structured data) via a REST based webservice.
* [Room](https://developer.android.com/topic/libraries/architecture/room) persistence library which provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
* [Cicerone](https://github.com/terrakok/Cicerone) is a lightweight library that makes the navigation in an Android app easy.
* [Material Design 3](https://material.io/develop/android/docs/getting-started/) an adaptable system of guidelines, components, and tools that support the best practices of user interface design.
* [View Binding](https://developer.android.com/topic/libraries/view-binding) is a feature that allows you to more easily write code that interacts with views..
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) lightweight threads. This allows us to write asynchronous non-blocking code in a consistent manner
* [Flow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) an addition to Kotlin coroutines for asynchronous processing of data streams that are executed sequentially.
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) to store and manage UI-related data in a lifecycle conscious way.
* [Unit tests](https://developer.android.com/studio/test/test-in-android-studio) to test individual parts of the application.
* [Android KTX](https://developer.android.com/kotlin/ktx) which helps to write more concise, idiomatic Kotlin code.
