package com.example.baseandroid.repository

import com.example.baseandroid.data.remote.ApiClient
import com.example.baseandroid.data.remote.ApiClientRefreshtor
import com.example.baseandroid.models.LoginResponse
import com.example.baseandroid.models.RefreshTokenResponse
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import javax.inject.Inject

interface AppRemoteDataRepositoryInterface {
    fun callLogin(email: String, password: String): Single<LoginResponse>
    fun refresh(token: String): Call<RefreshTokenResponse>
}

class AppRemoteDataRepository @Inject constructor(private val apiClient: ApiClient, private val apiClientRefreshtor: ApiClientRefreshtor) : AppRemoteDataRepositoryInterface {

    @Inject lateinit var gson: Gson

    override fun callLogin(email: String, password: String): Single<LoginResponse> {
        return apiClient.callLogin(email, password)
            .onErrorResumeNext {
                when (it) {
                    is HttpException -> {
                        val adapter = gson.getAdapter(LoginResponse::class.java)
                        try {
                            val json = adapter.fromJson(it.response()?.errorBody()?.string())
                            return@onErrorResumeNext Single.just(json)
                        } catch (error: IOException) {
                            return@onErrorResumeNext Single.just(
                                LoginResponse().apply {
                                    message = "Can not parser json"
                                }
                            )
                        }
                    }
                    is ConnectException -> {
                        return@onErrorResumeNext Single.just(
                            LoginResponse().apply {
                                message = "Network error"
                            }
                        )
                    }
                    else -> {
                        return@onErrorResumeNext Single.just(
                            LoginResponse().apply {
                                message = "Unknown error"
                            }
                        )
                    }
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun refresh(token: String): Call<RefreshTokenResponse> {
        return apiClientRefreshtor.refresh(token)
    }
}
