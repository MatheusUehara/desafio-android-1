package com.picpay.desafio.android.repository

import com.picpay.desafio.android.api.PicPayService

class PicPayRepositoryImpl(private val service: PicPayService) : PicPayRepository {

    override fun getUsers() = service.getUsers()

}
