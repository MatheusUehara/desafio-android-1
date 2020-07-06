package com.picpay.desafio.android.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.model.UiLoading
import com.picpay.desafio.android.model.UiState
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.usecase.UserUseCase
import kotlinx.coroutines.launch

class UserViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    private val _contacts: MutableLiveData<UiState<List<User>>> = MutableLiveData()
    val contacts: MutableLiveData<UiState<List<User>>> = _contacts

    fun fetchUsers() {
        Log.d("UEHARA"," FETCHING USERS")
        viewModelScope.launch {
            _contacts.value = UiLoading
            _contacts.value = userUseCase.getUsers()
        }
    }
}