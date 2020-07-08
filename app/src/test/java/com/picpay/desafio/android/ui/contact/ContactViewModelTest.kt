package com.picpay.desafio.android.ui.contact

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.model.*
import com.picpay.desafio.android.usecase.UserUseCase
import com.picpay.desafio.android.util.mockAndResponse
import com.picpay.desafio.android.view.contact.ContactViewModel
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor

@RunWith(JUnit4::class)
class ContactViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val useCase: UserUseCase = mock()

    @Test
    fun whenInitViewModelShould_callGetUsers() {
        mockUiSuccess()
        val observer: Observer<UiState<*>> = mock()
        val viewModel = ContactViewModel(useCase)
        viewModel.contacts.observe(mockLifecycleOwner(), observer)
        verify(useCase).getUsers()
    }

    @Test
    fun whenInitViewModelWithSuccess_shouldEmmitUiSuccess() {
        mockUiSuccess()
        val captor = ArgumentCaptor.forClass(UiState::class.java)
        val observer: Observer<UiState<*>> = mock()
        val viewModel = ContactViewModel(useCase)

        viewModel.contacts.observe(mockLifecycleOwner(), observer)

        verify(observer).onChanged(captor.capture())

        assertEquals(UiSuccess::class.java, captor.value.javaClass)
    }

    @Test
    fun whenInitViewModelWithLoading_shouldEmmitUiLoading() {
        mockUiLoading()
        val captor = ArgumentCaptor.forClass(UiState::class.java)
        val observer: Observer<UiState<*>> = mock()
        val viewModel = ContactViewModel(useCase)

        viewModel.contacts.observe(mockLifecycleOwner(), observer)

        verify(observer).onChanged(captor.capture())

        assertEquals(UiLoading::class.java, captor.value.javaClass)
    }

    @Test
    fun whenInitViewModelWithError_shouldEmmitUiError() {
        mockUiError()
        val captor = ArgumentCaptor.forClass(UiState::class.java)
        val observer: Observer<UiState<*>> = mock()
        val viewModel = ContactViewModel(useCase)

        viewModel.contacts.observe(mockLifecycleOwner(), observer)

        verify(observer).onChanged(captor.capture())

        assertEquals(UiError::class.java, captor.value.javaClass)
    }

    private fun mockUiSuccess(){
        whenever(useCase.getUsers()).mockAndResponse(UiSuccess(listOf(User())))
    }

    private fun mockUiError(){
        whenever(useCase.getUsers()).mockAndResponse(UiError(ErrorData(Throwable())))
    }

    private fun mockUiLoading(){
        whenever(useCase.getUsers()).mockAndResponse(UiLoading())
    }

    private fun mockLifecycleOwner(): LifecycleOwner {
        val owner: LifecycleOwner = mock()
        val lifecycle = LifecycleRegistry(owner)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        whenever(owner.lifecycle).thenReturn(lifecycle)
        return owner
    }
}