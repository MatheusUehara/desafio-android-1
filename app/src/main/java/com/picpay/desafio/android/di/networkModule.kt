package com.picpay.desafio.android.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.api.PicPayService
import com.picpay.desafio.android.repository.PicPayRepository
import com.picpay.desafio.android.repository.PicPayRepositoryImpl
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECTION_TIMEOUT = 15L
private const val READ_TIMEOUT = 30L
private const val CACHE_SIZE = (10 * 1024 * 1024).toLong()
private const val CACHE_TIME = 60 * 60 * 24

val networkModule = module {

    single {
        GsonBuilder().create()
    }

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("NETWORK: ", message)
            }
        }).apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single<Interceptor> {
        var isConnected = false

        val connectivityManager =
            androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        activeNetwork?.let { isConnected = it.isConnected }

        object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                request = if (isConnected)
                    request.newBuilder().header("Cache-Control", "public, max-age=5").build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=$CACHE_TIME"
                    ).build()
                return chain.proceed(request)
            }
        }
    }

    single<OkHttpClient> {
        val myCache = Cache(androidContext().cacheDir, CACHE_SIZE)
        OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor(get<Interceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single<PicPayService> { get<Retrofit>().create(PicPayService::class.java) }

    single<PicPayRepository> { PicPayRepositoryImpl(get()) }
}