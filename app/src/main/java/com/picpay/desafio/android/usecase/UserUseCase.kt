package com.picpay.desafio.android.usecase

import com.picpay.desafio.android.model.UiState
import com.picpay.desafio.android.model.User

interface UserUseCase {
    suspend fun getUsers(): UiState<List<User>>
}
