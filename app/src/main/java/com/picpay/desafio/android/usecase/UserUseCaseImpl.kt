package com.picpay.desafio.android.usecase

import com.picpay.desafio.android.model.UiState
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.repository.PicPayRepository

class UserUseCaseImpl(private val repository: PicPayRepository) : UserUseCase {

    override suspend fun getUsers(): UiState<List<User>> {
        return repository.getUsers()
    }

}