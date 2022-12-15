package ru.aleshin.news_settings_feature_impl.di.modules

import dagger.Binds
import dagger.Module
import ru.aleshin.core.di.FeatureScope
import ru.aleshin.news_settings_feature_impl.data.repository.SettingsRepositoryImpl
import ru.aleshin.news_settings_feature_impl.domain.repository.SettingsRepository

/**
 * @author Stanislav Aleshin on 27.10.2022.
 */
@Module
internal interface DataModule {

    @Binds
    @FeatureScope
    fun provideSettingsRepository(repository: SettingsRepositoryImpl): SettingsRepository
}