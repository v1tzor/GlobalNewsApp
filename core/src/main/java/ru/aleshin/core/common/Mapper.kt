package ru.aleshin.core.common

/**
 * @author Stanislav Aleshin on 07.10.2022.
 */
interface Mapper<I, O> {
    fun map(input: I): O
}