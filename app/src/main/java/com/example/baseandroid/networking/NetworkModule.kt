package com.example.baseandroid.networking
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    private const val APP_BASE_URL = "http://localhost.charlesproxy.com:3000/"

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun provideAppApi(): AppApi {
        return Retrofit.Builder()
            .baseUrl(APP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(createHttpClient())
            .build()
            .create(AppApi::class.java)
    }

}



