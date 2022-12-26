pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("com.android")) {
                useModule("com.android.tools.build:gradle:7.3.1")
            }
            if (requested.id.id.startsWith("org.jetbrains.kotlin")) {
                useVersion("1.7.20")
            }
            if (requested.id.id.startsWith("com.google.gms")) {
                useModule("com.google.gms:google-services:4.3.14")
            }
            if (requested.id.id.startsWith("com.google.firebase")) {
                useModule("com.google.firebase:firebase-crashlytics-gradle:2.9.2")
            }
        }
    }
}
rootProject.name = "GlobalNews"
include(":app")
include(":core")
include(":core-db")
include(":core-ui")
include(":module-injector")
include(":news_feature_api")
include(":news_feature_impl")
include(":news_settings_feature_api")
include(":news_settings_feature_impl")
include(":news_details_feature_impl")
include(":news_details_feature_api")
