package com.picpay.desafio.android.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.mockito.stubbing.OngoingStubbing

fun <T> OngoingStubbing<out LiveData<T>>.mockAndResponse(
    response: T,
    liveData: LiveData<T> = MutableLiveData()
) {
    thenAnswer { _ -> liveData.also { (it as MutableLiveData).postValue(response) }}
}