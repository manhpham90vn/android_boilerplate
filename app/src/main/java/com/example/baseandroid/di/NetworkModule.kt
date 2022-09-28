package com.example.baseandroid.di

import com.example.baseandroid.BuildConfig
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
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun createTokenInterceptor(appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface): TokenInterceptor {
        return TokenInterceptor(appLocalDataRepositoryInterface)
    }

    @Provides
    @Singleton
    fun createRefreshTokenAuthenticator(
        appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface,
        appRemoteDataRepositoryInterface: AppRemoteDataRepositoryInterface
    ): RefreshTokenAuthenticator {
        return RefreshTokenAuthenticator(appLocalDataRepositoryInterface, appRemoteDataRepositoryInterface)
    }

    @Provides
    @Singleton
    fun createHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Provides
    @Singleton
    fun createGson(): Gson {
        return GsonBuilder()
            .setPrettyPrinting()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun createRxJava3CallAdapterFactory(): RxJava3CallAdapterFactory {
        return RxJava3CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    @Named("httpClient")
    fun createHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .retryOnConnectionFailure(false)
            .build()
    }

    @Provides
    @Singleton
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

    @Provides
    @Singleton
    @Named("retrofit")
    fun createRetrofit(
        @Named("httpClient") httpClient: OkHttpClient,
        gson: Gson,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named("retrofitRefreshtor")
    fun createRetrofitRefreshtor(
        @Named("httpClient") httpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named("retrofitRefreshable")
    fun createRetrofitRefreshable(
        @Named("httpClientRefreshable") httpClient: OkHttpClient,
        gson: Gson,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiClient(@Named("retrofit") retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideApiClientRefreshable(@Named("retrofitRefreshable") retrofit: Retrofit): ApiClientRefreshable {
        return retrofit.create(ApiClientRefreshable::class.java)
    }

    @Provides
    @Singleton
    fun provideApiClientRefreshtor(@Named("retrofitRefreshtor") retrofit: Retrofit): ApiClientRefreshtor {
        return retrofit.create(ApiClientRefreshtor::class.java)
    }
}
