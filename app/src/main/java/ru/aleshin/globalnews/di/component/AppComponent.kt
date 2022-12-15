package ru.aleshin.globalnews.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.aleshin.core.di.AppScope
import ru.aleshin.globalnews.di.module.CoreDataModule
import ru.aleshin.globalnews.di.module.FeatureDependenciesModule
import ru.aleshin.globalnews.di.module.FeatureModule
import ru.aleshin.globalnews.di.module.NavigationModule
import ru.aleshin.globalnews.di.module.UtilsModule
import ru.aleshin.globalnews.di.module.ViewModelModule
import ru.aleshin.globalnews.presentation.activity.MainActivity
import ru.aleshin.globalnews.presentation.nav.NavFragment
import ru.aleshin.news_details_feature_impl.di.NewsDetailsFeatureDependencies
import ru.aleshin.news_feature_impl.di.NewsFeatureDependencies
import ru.aleshin.news_settings_feature_impl.di.NewsSettingsFeatureDependencies

/**
 * @author Stanislav Aleshin on 28.10.2022.
 */
@AppScope
@Component(
    modules = [
        UtilsModule::class,
        CoreDataModule::class,
        NavigationModule::class,
        FeatureModule::class,
        FeatureDependenciesModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AppComponentDependencies {

    fun inject(activity: MainActivity)

    fun inject(fragment: NavFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance fun applicationContext(context: Context): Builder
        fun coreDataModule(module: CoreDataModule): Builder
        fun navigationModule(module: NavigationModule): Builder
        fun featureModule(module: FeatureModule): Builder
        fun build(): AppComponent
    }

    companion object {
        fun create(context: Context) = DaggerAppComponent.builder()
            .applicationContext(context)
            .coreDataModule(CoreDataModule())
            .navigationModule(NavigationModule())
            .featureModule(FeatureModule())
            .build()
    }
}

interface AppComponentDependencies :
    NewsFeatureDependencies,
    NewsDetailsFeatureDependencies,
    NewsSettingsFeatureDependencies