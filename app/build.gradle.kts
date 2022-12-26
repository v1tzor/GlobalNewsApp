import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt") version "1.8.0-RC2"
    id("com.google.firebase.crashlytics")
    id("com.google.gms.google-services")
}

repositories {
    google()
    mavenCentral()
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.applicationId

        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = Config.testInstrumentationRunner
    }

    signingConfigs {
        val localProperties = gradleLocalProperties(rootDir)
        create("release") {
            storeFile = file(localProperties.getProperty("storeFile"))
            storePassword = localProperties.getProperty("storePassword")
            keyAlias = localProperties.getProperty("keyAlias")
            keyPassword = localProperties.getProperty("keyPassword")
        }
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
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

    implementation(project(":core"))
    implementation(project(":core-db"))
    implementation(project(":core-ui"))

    implementation(project(":news_feature_impl"))
    implementation(project(":news_feature_api"))

    implementation(project(":news_details_feature_api"))
    implementation(project(":news_details_feature_impl"))

    implementation(project(":news_settings_feature_api"))
    implementation(project(":news_settings_feature_impl"))

    // AndroidX
    implementation(Dependencies.AndroidX.core)
    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.AndroidX.material)

    // Cicerone
    implementation(Dependencies.Cicerone.library)

    // Dagger
    implementation(Dependencies.Dagger.library)
    kapt(Dependencies.Dagger.compiler)

    // Room
    implementation(Dependencies.Room.library)
    kapt(Dependencies.Room.compiler)

    // Test
    testImplementation(Dependencies.Test.jUnit)
    androidTestImplementation(Dependencies.Test.jUnitExt)
    androidTestImplementation(Dependencies.Test.espresso)

    // Firebase
    implementation(platform(Dependencies.FireBase.bom))
    implementation(Dependencies.FireBase.crashlytics)
    implementation(Dependencies.FireBase.analytics)

    // Leakcanary
    debugImplementation(Dependencies.Leakcanary.library)
}