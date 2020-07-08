package com.picpay.desafio.android.model

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed class UiState<in T> {
    companion object {
        fun <T> create(throwable: Throwable): UiState<T> {
            return when (throwable) {
                is IOException -> {
                    UiError<T>(ErrorData(throwable))
                }
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = throwable.message()
                    UiError<T>(ErrorData(throwable, code, errorResponse))
                }
                else -> {
                    UiError<T>(ErrorData(throwable))
                }
            }
        }

        fun <T> create(response: Response<T>): UiState<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                UiSuccess(body)
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) response.message() else msg
                return UiError(ErrorData(Throwable(errorMsg ?: "unknown error")))
            }
        }
    }
}
class UiLoading<T> : UiState<T>()
data class UiSuccess<T>(val data: T) : UiState<T>()
data class UiError<T>(val errorData: ErrorData) : UiState<T>()
data class ErrorData(val throwable: Throwable, val code: Int? = 0, val message: String? = String())
