package ru.aleshin.core.handlers

/**
 * @author Stanislav Aleshin on 14.10.2022.
 */
interface ErrorHandler<T> {
    fun handle(throwable: Throwable): T
}