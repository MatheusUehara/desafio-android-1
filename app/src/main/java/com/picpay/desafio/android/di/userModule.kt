package com.picpay.desafio.android.di

import com.picpay.desafio.android.usecase.UserUseCase
import com.picpay.desafio.android.view.UserViewModel
import com.picpay.desafio.android.usecase.UserUseCaseImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userModule = module {
    factory<UserUseCase> { UserUseCaseImpl(get()) }
    viewModel { UserViewModel(get()) }
}