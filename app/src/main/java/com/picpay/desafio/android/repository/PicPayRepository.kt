package com.picpay.desafio.android.repository

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.model.UiState
import com.picpay.desafio.android.model.User

interface PicPayRepository {
    fun getUsers(): LiveData<UiState<List<User>>>
}