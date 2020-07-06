package com.picpay.desafio.android.repository

import com.picpay.desafio.android.model.UiState
import com.picpay.desafio.android.model.User

interface PicPayRepository {
    suspend fun getUsers(): UiState<List<User>>
}