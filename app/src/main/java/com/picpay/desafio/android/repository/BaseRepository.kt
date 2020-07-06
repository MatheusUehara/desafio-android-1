package com.picpay.desafio.android.repository

import com.picpay.desafio.android.model.ErrorData
import com.picpay.desafio.android.model.UiError
import com.picpay.desafio.android.model.UiState
import com.picpay.desafio.android.model.UiSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

abstract class BaseRepository() {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): UiState<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall.invoke()
                UiSuccess(response)
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> {
                        UiError<T>(ErrorData(throwable))
                    }
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = throwable.message()
                        UiError<T>(ErrorData(throwable, code, errorResponse))
                    }
                    else -> {
                        UiError<T>(ErrorData(Throwable("Unexpected Error")))
                    }
                }
            }
        }
    }
}
