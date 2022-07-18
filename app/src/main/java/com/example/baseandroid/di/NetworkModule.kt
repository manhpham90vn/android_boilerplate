package com.example.baseandroid.di

import com.example.baseandroid.data.remote.ApiClient
import com.example.baseandroid.data.remote.ApiClientRefreshable
import com.example.baseandroid.data.remote.ApiClientRefreshtor
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
    fun createRefreshTokenAuthenticator(
        appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface,
        appRemoteDataRepositoryInterface: AppRemoteDataRepositoryInterface
    ): RefreshTokenAuthenticator {
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
    fun createRxJava3CallAdapterFactory(): RxJava3CallAdapterFactory {
        return RxJava3CallAdapterFactory.create()
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

    @AppScope
    @Provides
    @Named("httpClientRefreshable")
    fun createHttpClientRefreshable(
        tokenInterceptor: TokenInterceptor,
        refreshTokenAuthenticator: RefreshTokenAuthenticator,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
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
    @Named("retrofit")
    fun createRetrofit(
        @Named("httpClient") httpClient: OkHttpClient,
        gson: Gson,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(APP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
            .client(httpClient)
            .build()
    }

    @AppScope
    @Provides
    @Named("retrofitRefreshtor")
    fun createRetrofitRefreshtor(
        @Named("httpClient") httpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(APP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
            .build()
    }

    @AppScope
    @Provides
    @Named("retrofitRefreshable")
    fun createRetrofitRefreshable(
        @Named("httpClientRefreshable") httpClient: OkHttpClient,
        gson: Gson,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(APP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
            .client(httpClient)
            .build()
    }

    @AppScope
    @Provides
    fun provideApiClient(@Named("retrofit") retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }

    @AppScope
    @Provides
    fun provideApiClientRefreshable(@Named("retrofitRefreshable") retrofit: Retrofit): ApiClientRefreshable {
        return retrofit.create(ApiClientRefreshable::class.java)
    }

    @AppScope
    @Provides
    fun provideApiClientRefreshtor(@Named("retrofitRefreshtor") retrofit: Retrofit): ApiClientRefreshtor {
        return retrofit.create(ApiClientRefreshtor::class.java)
    }
}
