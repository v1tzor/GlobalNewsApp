package ru.aleshin.core_db.exceptions

import ru.aleshin.core.exceptions.DomainException

/**
 * @author Stanislav Aleshin on 22.10.2022.
 */

abstract class StorageExceptions : DomainException()

class StorageReadException : StorageExceptions()

class StorageUpdateException : StorageExceptions()

class StorageDeleteException : StorageExceptions()

class StorageWriteException : StorageExceptions()