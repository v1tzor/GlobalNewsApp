/**
 * @author Stanislav Aleshin on 26.12.2022.
 */
object Dependencies {

    object AndroidX {
        const val core = "androidx.core:core-ktx:${Versions.core}"

        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val material = "com.google.android.material:material:${Versions.material}"
        const val refreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.refreshLayout}"

        const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

        const val paging = "androidx.paging:paging-runtime:${Versions.paging}"
    }

    object Test {
        const val jUnit = "junit:junit:${Versions.jUnit}"
        const val jUnitExt = "androidx.test.ext:junit:${Versions.jUnitExt}"

        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
        const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"

        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

        const val fragmentTest = "androidx.fragment:fragment-testing:${Versions.fragmentTest}"
    }

    object FireBase {
        const val bom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
        const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
        const val analytics = "com.google.firebase:firebase-analytics-ktx"
    }

    object Dagger {
        const val library = "com.google.dagger:dagger:${Versions.dagger}"
        const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    }

    object Retrofit {
        const val library = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val convertor = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    }

    object Room {
        const val library = "androidx.room:room-runtime:${Versions.room}"
        const val compiler = "androidx.room:room-compiler:${Versions.room}"
    }

    object Leakcanary {
        const val library = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary}"
    }

    object Cicerone {
        const val library = "com.github.terrakok:cicerone:${Versions.cicerone}"
    }

    object Glide {
        const val library = "com.github.bumptech.glide:glide:${Versions.glide}"
    }

    object Shimmer {
        const val library = "com.facebook.shimmer:shimmer:${Versions.shimmer}"
    }
}