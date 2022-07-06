package com.example.baseandroid.networking

import com.example.baseandroid.data.ApiClient
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkModule(private val appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface) {

    companion object {
        private const val APP_BASE_URL = "http://localhost.charlesproxy.com:3000/"
    }

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(TokenInterceptor(appLocalDataRepositoryInterface))
            .authenticator(RefreshTokenAuthenticator(appLocalDataRepositoryInterface))
            .addNetworkInterceptor(HttpLoggingInterceptor()
                .apply {
                    this.setLevel(HttpLoggingInterceptor.Level.BODY)
                })
            .retryOnConnectionFailure(false)
            .build()
    }

    fun provideAppApi(): ApiClient {
        return Retrofit.Builder()
            .baseUrl(APP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(createHttpClient())
            .build()
            .create(ApiClient::class.java)
    }
}



