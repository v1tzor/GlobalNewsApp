package ru.aleshin.core.exceptions

import retrofit2.HttpException

/**
 * @author Stanislav Aleshin on 14.10.2022.
 */

abstract class DomainException : IllegalStateException()

class NetworkException : DomainException()

class ServiceUnavailableException(val httpException: HttpException) : DomainException()

class DataBaseException(val throwable: Throwable) : DomainException()