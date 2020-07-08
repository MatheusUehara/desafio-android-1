package com.picpay.desafio.android

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class PicPayTestRunner: AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, PicPayTestApplication::class.java.name, context)
    }
}
