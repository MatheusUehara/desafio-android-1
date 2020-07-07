package com.picpay.desafio.android.usecase

import com.picpay.desafio.android.repository.PicPayRepository

class UserUseCaseImpl(private val repository: PicPayRepository) : UserUseCase {

    override fun getUsers() = repository.getUsers()

}