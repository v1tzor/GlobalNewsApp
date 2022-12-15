package ru.aleshin.core.handlers

import ru.aleshin.core.functional.Either

/**
 * @author Stanislav Aleshin on 14.10.2022.
 */
interface EitherFailureWrapper<L> {

    suspend fun <R> wrap(block: suspend () -> R): Either<L, R>

    abstract class Abstract<L>(
        private val errorHandler: ErrorHandler<L>
    ) : EitherFailureWrapper<L> {

        override suspend fun <R> wrap(block: suspend () -> R) = try {
            Either.Right(block.invoke())
        } catch (e: Throwable) {
            Either.Left(errorHandler.handle(e))
        }
    }
}
