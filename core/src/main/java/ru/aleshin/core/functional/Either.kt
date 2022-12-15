package ru.aleshin.core.functional

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
sealed class Either<out L, out R> {

    class Left<out L>(val data: L) : Either<L, Nothing>()

    class Right<out R>(val data: R) : Either<Nothing, R>()

    val isLeft get() = this is Left

    val isRight get() = this is Right
}

typealias EitherLeft<T> = Either<T, Unit>

typealias RightEither<T> = Either<Unit, T>
