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
        consumerProguardFiles(Config.consumerProguardFiles)
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
    implementation(project(":core-ui"))

    // AndroidX
    implementation(Dependencies.AndroidX.core)
    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.AndroidX.material)

    // Lifecycle
    implementation(Dependencies.AndroidX.lifecycleViewModel)
    implementation(Dependencies.AndroidX.lifecycleRuntime)

    // Paging 3
    implementation(Dependencies.AndroidX.paging)

    // Glide
    implementation(Dependencies.Glide.library)

    // Cicerone
    implementation(Dependencies.Cicerone.library)

    // Dagger
    implementation(Dependencies.Dagger.library)
    kapt(Dependencies.Dagger.compiler)

    // Retrofit
    implementation(Dependencies.Retrofit.library)

    // Test
    testImplementation(Dependencies.Test.jUnit)
    androidTestImplementation(Dependencies.Test.espresso)
    androidTestImplementation(Dependencies.Test.jUnitExt)
    androidTestImplementation(Dependencies.Test.coroutinesTest)
}