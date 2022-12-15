package ru.aleshin.globalnews.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.aleshin.core.di.AppScope
import ru.aleshin.core_db.details.NewsDetailsLocalDataSource
import ru.aleshin.core_db.settings.SettingsDao
import ru.aleshin.core_db.settings.SettingsDataBase
import ru.aleshin.core_db.settings.SettingsLocalDataSource

/**
 * @author Stanislav Aleshin on 28.10.2022.
 */
@Module
class CoreDataModule {

    @Provides
    @AppScope
    fun provideSettingsDataBase(
        context: Context
    ): SettingsDataBase {
        return Room
            .databaseBuilder(context, SettingsDataBase::class.java, SettingsDataBase.DATABASE_NAME)
            .createFromAsset(SettingsDataBase.PREPOPULATE_DATABASE_NAME)
            .build()
    }

    @Provides
    @AppScope
    fun provideSettingsDao(dataBase: SettingsDataBase): SettingsDao {
        return dataBase.fetchSettingsDao()
    }

    @Provides
    @AppScope
    fun provideSettingsLocalDataSource(dao: SettingsDao): SettingsLocalDataSource {
        return SettingsLocalDataSource.Base(dao)
    }

    @Provides
    @AppScope
    fun provideNewsDetailsLocalDataSource(): NewsDetailsLocalDataSource {
        return NewsDetailsLocalDataSource.Base()
    }
}