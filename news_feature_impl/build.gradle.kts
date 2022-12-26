import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt") version "1.8.0-RC2"
}

repositories {
    google()
    mavenCentral()
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

        testInstrumentationRunner = Config.testInstrumentationRunner
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                Config.consumerProguardFiles
            )
        }
    }

    buildTypes.forEach {
        val globalNewsApiKey = gradleLocalProperties(rootDir).getProperty("global_news_api_key")
        val globalNewsBaseUrl = gradleLocalProperties(rootDir).getProperty("global_news_base_url")

        it.buildConfigField("String", "GNEWS_API_KEY", globalNewsApiKey)
        it.buildConfigField("String", "GNEWS_BASE_URL", globalNewsBaseUrl)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // Modules
    implementation(project(":module-injector"))

    implementation(project(":core"))
    implementation(project(":core-db"))
    implementation(project(":core-ui"))

    implementation(project(":news_feature_api"))

    implementation(project(":news_settings_feature_api"))
    implementation(project(":news_details_feature_api"))

    // AndroidX
    implementation(Dependencies.AndroidX.core)
    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.AndroidX.material)
    implementation(Dependencies.AndroidX.refreshLayout)

    // Lifecycle
    implementation(Dependencies.AndroidX.lifecycleViewModel)
    implementation(Dependencies.AndroidX.lifecycleRuntime)

    // Dagger
    implementation(Dependencies.Dagger.library)
    kapt(Dependencies.Dagger.compiler)

    // Paging 3
    implementation(Dependencies.AndroidX.paging)

    // Cicerone
    implementation(Dependencies.Cicerone.library)

    // Shimmer
    implementation(Dependencies.Shimmer.library)

    // Retrofit
    implementation(Dependencies.Retrofit.library)
    implementation(Dependencies.Retrofit.convertor)
    implementation(Dependencies.Retrofit.loggingInterceptor)

    // Test
    testImplementation(Dependencies.Test.jUnit)
    androidTestImplementation(Dependencies.Test.espresso)
    androidTestImplementation(Dependencies.Test.jUnitExt)
    androidTestImplementation(Dependencies.Test.coroutinesTest)
    androidTestImplementation(Dependencies.Test.turbine)
}