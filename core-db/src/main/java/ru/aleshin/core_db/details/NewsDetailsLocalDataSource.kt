package ru.aleshin.core_db.details

import ru.aleshin.core_db.exceptions.StorageReadException

/**
 * @author Stanislav Aleshin on 15.10.2022.
 */
interface NewsDetailsLocalDataSource {

    suspend fun read(): NewsDetailsModel

    suspend fun save(news: NewsDetailsModel)

    class Base : NewsDetailsLocalDataSource {

        private var localStorage: NewsDetailsModel? = null

        override suspend fun read(): NewsDetailsModel {
            return localStorage ?: throw StorageReadException()
        }

        override suspend fun save(news: NewsDetailsModel) {
            localStorage = news
        }
    }
}