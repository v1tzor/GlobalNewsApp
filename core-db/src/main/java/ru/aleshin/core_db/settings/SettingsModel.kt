package ru.aleshin.core_db.settings

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.aleshin.core.common.Mapper

/**
 * @author Stanislav Aleshin on 09.10.2022.
 */
@Entity(tableName = "settings")
data class SettingsModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "language")
    val language: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "defualt_category")
    val defualtCategory: String
) {
    fun <O> map(mapper: Mapper<SettingsModel, O>) = mapper.map(this)
}