package ru.aleshin.core.handlers

import retrofit2.HttpException
import retrofit2.Response
import ru.aleshin.core.functional.ResponseResult
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
interface ResponseHandler<T> {

    suspend fun handle(block: suspend () -> Response<T>): ResponseResult<T>

    class Base<T> @Inject constructor() : ResponseHandler<T> {

        override suspend fun handle(block: suspend () -> Response<T>) = try {
            val response = block.invoke()
            if (response.isSuccessful) {
                if (response.body() == null || response.body().toString().isEmpty()) {
                    ResponseResult.Success.Empty(null, response.code())
                } else {
                    ResponseResult.Success.Data(checkNotNull(response.body()), response.code())
                }
            } else {
                ResponseResult.Error(HttpException(response))
            }
        } catch (throwable: Throwable) {
            ResponseResult.Error(throwable)
        }
    }
}