package com.picpay.desafio.android.repository

import com.picpay.desafio.android.api.PicPayService
import com.picpay.desafio.android.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Response
import retrofit2.HttpException
import java.io.IOException

class PicPayRepositoryImpl(private val service: PicPayService) : PicPayRepository, BaseRepository() {

    override suspend fun getUsers(): UiState<List<User>> = safeApiCall(service::getUsers)

}


