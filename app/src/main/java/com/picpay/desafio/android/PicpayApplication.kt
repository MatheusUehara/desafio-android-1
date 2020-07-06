package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.networkModule
import com.picpay.desafio.android.di.userModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PicpayApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PicpayApplication)
            modules(listOf(networkModule, userModule))
        }
    }
}