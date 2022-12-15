package ru.aleshin.core_db.settings

import ru.aleshin.core_db.exceptions.StorageReadException
import ru.aleshin.core_db.exceptions.StorageUpdateException

/**
 * @author Stanislav Aleshin on 09.10.2022.
 */
interface SettingsLocalDataSource {

    suspend fun fetchSettings(): SettingsModel

    suspend fun updateSettings(data: SettingsModel)

    class Base(private val dao: SettingsDao) : SettingsLocalDataSource {

        override suspend fun fetchSettings(): SettingsModel {
            return dao.fetchSettings() ?: throw StorageReadException()
        }

        override suspend fun updateSettings(data: SettingsModel) {
            return dao.updateSettings(data).let {
                if (it == 0) throw StorageUpdateException()
            }
        }
    }
}
