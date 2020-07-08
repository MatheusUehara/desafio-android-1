package com.picpay.desafio.android.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.api.PicPayService
import com.picpay.desafio.android.model.UiSuccess
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.util.mockAndResponse
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class PicPayRepositoryTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val api = mock<PicPayService>()

    private val repository = PicPayRepositoryImpl(api)

    @Test
    fun whenGetUsers_shouldReturnUiSuccess() {
        val expectedResponse = UiSuccess(emptyList<User>())

        whenever(api.getUsers()).mockAndResponse(expectedResponse)

        val users = repository.getUsers().value

        assertEquals(expectedResponse, users)
    }
}