package com.picpay.desafio.android.view.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.picpay.desafio.android.model.UiState
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.usecase.UserUseCase

class ContactViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    private var _contacts = MutableLiveData(Unit)
    val contacts: LiveData<UiState<List<User>>> = _contacts.switchMap { fetchContacts() }

    init {
        loadContacts()
    }

    private fun fetchContacts(): LiveData<UiState<List<User>>> = userUseCase.getUsers()

    fun loadContacts(){
        _contacts.value = Unit
    }
}