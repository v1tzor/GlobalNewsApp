package ru.aleshin.core_db.settings

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author Stanislav Aleshin on 09.10.2022.
 */
@Database(entities = [SettingsModel::class], version = 1)
abstract class SettingsDataBase : RoomDatabase() {

    abstract fun fetchSettingsDao(): SettingsDao

    companion object {
        const val DATABASE_NAME = "global_news_settings.db"
        const val PREPOPULATE_DATABASE_NAME = "database/global_news_settings_prepopulate.db"
    }
}