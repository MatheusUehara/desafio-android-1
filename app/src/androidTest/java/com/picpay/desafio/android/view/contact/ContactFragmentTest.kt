package com.picpay.desafio.android.view.contact

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.picpay.desafio.android.R
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.util.RecyclerViewMatchers.checkRecyclerViewItem
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class ContactFragmentTest {

    private val server = MockWebServer()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setup() {
        server.start(serverPort)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    private fun launchFragment() = launchFragmentInContainer<ContactsFragment>()

    @Test
    fun whenStartFragment_shouldDisplayLoading() {
        launchFragment().apply{
            onView(withId(R.id.loading_view)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun whenStartFragmentWithError_shouldDisplayErrorMessage() {
        mockResponseError()
        launchFragment().apply {
            Thread.sleep(500)
            onView(withId(R.id.error_title)).check(matches(isDisplayed()))
            onView(withId(R.id.error_retry_button)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun whenStartFragment_shouldDisplayTitle() {
        mockSuccessResponse()
        val expectedTitle = context.getString(R.string.title)

        launchFragment().apply {
            Thread.sleep(500)
            onView(withId(R.id.title)).check(
                matches(
                    allOf(
                        isDisplayed(),
                        withText(expectedTitle)
                    )
                )
            )
        }
    }

    @Test
    fun shouldDisplayListItem() {
        mockSuccessResponse()
        launchFragment().apply {
            Thread.sleep(500)
            checkRecyclerViewItem(R.id.contacts_recyclerView, 0, withText("Eduardo Santos"))
        }
    }

    private fun setResponse(responseJson: String, code: Int, delay: Long = 0) {
        val mockResponse = MockResponse()
            .setResponseCode(code)
            .setBody(responseJson)
            .setBodyDelay(delay, TimeUnit.MILLISECONDS)
        server.enqueue(mockResponse)
    }

    private fun mockSuccessResponse(delay: Long = 0) {
        val response = listOf(
            User(
                "https://randomuser.me/api/portraits/men/9.jpg",
                "Eduardo Santos",
                1001,
                "@eduardo.santos"
            )
        )
        setResponse(Gson().toJson(response), HttpURLConnection.HTTP_OK, delay)
    }

    private fun mockResponseError() {
        setResponse("", HttpURLConnection.HTTP_BAD_REQUEST)
    }

    companion object {
        private const val serverPort = 8080
    }

}