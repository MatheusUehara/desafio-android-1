package com.picpay.desafio.android.usecase

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.model.UiState
import com.picpay.desafio.android.model.User

interface UserUseCase {
    fun getUsers(): LiveData<UiState<List<User>>>
}
