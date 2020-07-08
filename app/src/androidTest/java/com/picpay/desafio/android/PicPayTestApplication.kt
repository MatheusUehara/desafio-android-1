package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.PROPERTY_BASE_URL
import com.picpay.desafio.android.di.networkModule
import com.picpay.desafio.android.di.userModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PicPayTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PicPayTestApplication)
            modules(listOf(networkModule, userModule))
            properties(mapOf(PROPERTY_BASE_URL to "http://localhost:8080/"))
        }
    }
}