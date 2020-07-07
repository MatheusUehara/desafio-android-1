package com.picpay.desafio.android.api

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.model.UiState
import com.picpay.desafio.android.model.User
import retrofit2.http.GET

interface PicPayService {

    @GET("users")
    fun getUsers(): LiveData<UiState<List<User>>>

}