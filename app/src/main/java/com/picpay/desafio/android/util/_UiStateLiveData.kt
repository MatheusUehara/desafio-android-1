package com.picpay.desafio.android.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import com.picpay.desafio.android.model.*

fun <T> LiveData<UiState<T>>.observeOnSuccess(
    viewLifecycleOwner: LifecycleOwner,
    callback: (data: T) -> Unit
): LiveData<UiState<T>> {
    this.observe(viewLifecycleOwner, { value ->
        if (value is UiSuccess) callback.invoke(value.data)
    })
    return this
}

fun <T> LiveData<UiState<T>>.observeOnError(
    viewLifecycleOwner: LifecycleOwner,
    callback: (ErrorData) -> Unit
): LiveData<UiState<T>> {
    this.observe(viewLifecycleOwner, { value ->
        if (value is UiError<*>) callback.invoke(value.errorData)
    })
    return this
}

fun <T> LiveData<UiState<T>>.observeOnLoading(
    viewLifecycleOwner: LifecycleOwner,
    callback: () -> Unit
): LiveData<UiState<T>> {
    this.observe(viewLifecycleOwner, { value ->
        if (value is UiLoading) callback.invoke()
    })
    return this
}