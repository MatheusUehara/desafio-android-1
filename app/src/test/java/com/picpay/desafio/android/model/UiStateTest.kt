package com.picpay.desafio.android.model

import com.picpay.desafio.android.model.UiState.Companion.create
import junit.framework.Assert.assertEquals
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


class UiStateTest {

    @Test
    fun whenCreateUiStateWithIOException_shouldReturnUiError() {
        val message = "My message"
        val throwable = IOException(message)
        val uiStateObject = create<Any>(throwable)
        assertEquals(UiError::class.java, uiStateObject.javaClass)
        assertEquals(throwable, (uiStateObject as UiError).errorData.throwable)
    }

    @Test
    fun whenCreateUiStateWithHttpException_shouldReturnUiError() {
        val message = "My message"
        val code = 403
        val response: Response<String> = Response.error(code, message.toResponseBody())
        val throwable = HttpException(response)
        val uiStateObject = create<Any>(throwable)
        assertEquals(UiError::class.java, uiStateObject.javaClass)
        assertEquals(code, (uiStateObject as UiError).errorData.code)
        assertEquals(throwable, (uiStateObject as UiError).errorData.throwable)
        assertEquals(response.message(), (uiStateObject as UiError).errorData.message)
    }

    @Test
    fun whenCreateUiStateWithThrowable_shouldReturnUiError() {
        val message = "Unexpected Error"
        val throwable = Throwable(message)
        val uiStateObject = create<Any>(throwable)
        assertEquals(UiError::class.java, uiStateObject.javaClass)
        assertEquals(throwable, (uiStateObject as UiError).errorData.throwable)
    }

    @Test
    fun whenCreateUiStateWithResponseError_shouldReturnUiError() {
        val message = "My message"
        val code = 403
        val response: Response<String> = Response.error(code, message.toResponseBody())
        val uiStateObject = create(response)
        assertEquals(UiError::class.java, uiStateObject.javaClass)
    }

    @Test
    fun whenCreateUiStateWithResponseStringSuccess_shouldReturnUiSuccess() {
        val message = "My message"
        val code = 200
        val response: Response<String> = Response.success(code, message)
        val uiStateObject = create(response)
        assertEquals(UiSuccess::class.java, uiStateObject.javaClass)
    }

    @Test
    fun whenCreateUiStateWithResponseUserSuccess_shouldReturnUiSuccess() {
        val user = User("", "uehara", 0, "Ueharinha")
        val code = 200
        val response: Response<User> = Response.success(code, user)
        val uiStateObject = create(response)
        assertEquals(UiSuccess::class.java, uiStateObject.javaClass)
        assertEquals(user, (uiStateObject as UiSuccess).data)
    }
}