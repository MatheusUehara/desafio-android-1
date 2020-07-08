package com.picpay.desafio.android.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.model.*
import org.junit.Rule
import org.junit.Test

class UiStateLiveDataTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun whenEmmitUiSuccess_shouldCallSuccessCallback() {
        val liveData: MutableLiveData<UiState<String>> = MutableLiveData()
        val callback: (String) -> Unit = mock()
        liveData.observeOnSuccess(mockLifecycleOwner(), callback)
        liveData.postValue(UiSuccess(""))
        verify(callback).invoke(any())
    }

    @Test
    fun whenEmmitUiLoading_shouldCallLoadingCallback() {
        val liveData: MutableLiveData<UiState<String>> = MutableLiveData()
        val callback: () -> Unit = mock()
        liveData.observeOnLoading(mockLifecycleOwner(), callback)
        liveData.postValue(UiLoading())
        verify(callback).invoke()
    }

    @Test
    fun whenEmmitUiError_shouldCallErrorCallback() {
        val liveData: MutableLiveData<UiState<String>> = MutableLiveData()
        val callback: (errorData: ErrorData) -> Unit = mock()
        val uiState: UiState<String> = UiError(ErrorData(Throwable()))
        liveData.observeOnError(mockLifecycleOwner(), callback)
        liveData.postValue(uiState)
        verify(callback).invoke(any())
    }

    @Test
    fun whenEmmitUiStateSequential_shouldCallAllCallbacks() {
        val liveData: MutableLiveData<UiState<String>> = MutableLiveData()
        val callbackError: (errorData: ErrorData) -> Unit = mock()
        val callbackSuccess: (value: String) -> Unit = mock()
        val callbackLoading: () -> Unit = mock()
        liveData
            .observeOnError(mockLifecycleOwner(), callbackError)
            .observeOnLoading(mockLifecycleOwner(), callbackLoading)
            .observeOnSuccess(mockLifecycleOwner(), callbackSuccess)
        liveData.postValue(UiLoading())
        liveData.postValue(UiError(ErrorData(Throwable())))
        liveData.postValue(UiSuccess(""))
        verify(callbackLoading).invoke()
        verify(callbackError).invoke(any())
        verify(callbackSuccess).invoke(any())
    }

    private fun mockLifecycleOwner(): LifecycleOwner {
        val owner: LifecycleOwner = mock()
        val lifecycle = LifecycleRegistry(owner)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        whenever(owner.lifecycle).thenReturn(lifecycle)
        return owner
    }
}