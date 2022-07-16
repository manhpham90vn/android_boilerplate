package com.example.baseandroid.di

import com.example.baseandroid.data.remote.ApiClient
import com.example.baseandroid.data.remote.ApiClientRefreshable
import com.example.baseandroid.networking.RefreshTokenAuthenticator
import com.example.baseandroid.networking.TokenInterceptor
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.repository.AppRemoteDataRepositoryInterface
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class NetworkModule {

    companion object {
        private const val APP_BASE_URL = "http://localhost.charlesproxy.com:3000/"
    }

    @AppScope
    @Provides
    fun createTokenInterceptor(appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface): TokenInterceptor {
        return TokenInterceptor(appLocalDataRepositoryInterface)
    }

    @AppScope
    @Provides
    fun createRefreshTokenAuthenticator(appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface,
                                        appRemoteDataRepositoryInterface: AppRemoteDataRepositoryInterface): RefreshTokenAuthenticator {
        return RefreshTokenAuthenticator(appLocalDataRepositoryInterface, appRemoteDataRepositoryInterface)
    }

    @AppScope
    @Provides
    fun createHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @AppScope
    @Provides
    fun createGson(): Gson {
        return GsonBuilder()
            .setPrettyPrinting()
            .setLenient()
            .create()
    }

    @AppScope
    @Provides
    @Named("httpClientRefreshable")
    fun createHttpClientRefreshable(tokenInterceptor: TokenInterceptor,
                                    refreshTokenAuthenticator: RefreshTokenAuthenticator,
                                    httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(tokenInterceptor)
            .authenticator(refreshTokenAuthenticator)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .retryOnConnectionFailure(false)
            .build()
    }

    @AppScope
    @Provides
    @Named("httpClient")
    fun createHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .retryOnConnectionFailure(false)
            .build()
    }

    private fun createRetrofit(httpClient: OkHttpClient,
                               gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(APP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(httpClient)
            .build()
    }

    @AppScope
    @Provides
    fun provideApiClient(@Named("httpClient") httpClient: OkHttpClient,
                         gson: Gson): ApiClient {
        return createRetrofit(httpClient, gson)
            .create(ApiClient::class.java)
    }

    @AppScope
    @Provides
    fun provideApiClientRefreshable(@Named("httpClientRefreshable") httpClient: OkHttpClient,
                                    gson: Gson): ApiClientRefreshable {
        return createRetrofit(httpClient, gson)
            .create(ApiClientRefreshable::class.java)
    }
}



