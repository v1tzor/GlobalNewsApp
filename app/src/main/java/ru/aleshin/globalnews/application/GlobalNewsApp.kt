package ru.aleshin.globalnews.application

import android.app.Application
import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import ru.aleshin.globalnews.di.component.AppComponent

/**
 * @author Stanislav Aleshin on 06.10.2022.
 */
class GlobalNewsApp : Application(), FetchAppComponent {

    private var appComponent: AppComponent? = null
    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = AppComponent.create(applicationContext)
        mFirebaseAnalytics = Firebase.analytics
    }

    override fun fetchAppComponent(): AppComponent {
        return checkNotNull(appComponent) { "AppComponent is not initializing" }
    }
}

fun Context.getApp(): GlobalNewsApp {
    return applicationContext as GlobalNewsApp
}