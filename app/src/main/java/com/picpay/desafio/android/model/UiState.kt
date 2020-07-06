package com.picpay.desafio.android.model

sealed class UiState<in T>
object UiLoading: UiState<Any>()
data class UiSuccess<T>(val data: T): UiState<T>()
data class UiError<T>(val errorData: ErrorData) : UiState<T>()
data class ErrorData(val throwable: Throwable, val code: Int? = 0, val message: String? = String())
