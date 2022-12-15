package ru.aleshin.core_db.settings

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

/**
 * @author Stanislav Aleshin on 09.10.2022.
 */
@Dao
interface SettingsDao {

    @Query("SELECT * FROM settings WHERE id = 1 LIMIT 1")
    fun fetchSettings(): SettingsModel?

    @Update
    fun updateSettings(settingsModel: SettingsModel): Int
}