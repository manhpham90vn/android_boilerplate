package com.example.baseandroid.networking

import com.example.baseandroid.common.ConnectivityService
import com.example.baseandroid.common.SchedulerProvider
import com.example.baseandroid.data.remote.Api
import com.example.baseandroid.models.ErrorResponse
import com.example.baseandroid.utils.applySchedulers
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.util.concurrent.TimeoutException

fun <T> Single<T>.makeRequest(
    schedulerProvider: SchedulerProvider,
    connectivityService: ConnectivityService,
    gson: Gson,
    api: Api
): Single<T> =
    applySchedulers(schedulerProvider)
        .checkConnectivity(connectivityService, api)
        .onErrorResumeNext {
            when (it) {
                is HttpException -> {
                    val adapter = gson.getAdapter(ErrorResponse::class.java)
                    try {
                        val json = adapter.fromJson(it.response()?.errorBody()?.string())
                        throw ApiException.ServerErrorException(json.code ?: "-1", json.message ?: "unknown error", api)
                    } catch (error: IOException) {
                        throw ApiException.ParseJSONException(api)
                    }
                }
                is ConnectException -> throw ApiException.NoInternetConnectionException(api)
                is TimeoutException -> throw ApiException.TimeOutException(api)
                else -> throw ApiException.UnknownException(api)
            }
        }

fun Completable.makeRequest(
    schedulerProvider: SchedulerProvider,
    connectivityService: ConnectivityService,
    gson: Gson,
    api: Api
): Completable =
    applySchedulers(schedulerProvider)
        .checkConnectivity(connectivityService, api)
        .onErrorResumeNext {
            when (it) {
                is HttpException -> {
                    val adapter = gson.getAdapter(ErrorResponse::class.java)
                    try {
                        val json = adapter.fromJson(it.response()?.errorBody()?.string())
                        throw ApiException.ServerErrorException(json.code ?: "-1", json.message ?: "unknown error", api)
                    } catch (error: IOException) {
                        throw ApiException.ParseJSONException(api)
                    }
                }
                is ConnectException -> throw ApiException.NoInternetConnectionException(api)
                is TimeoutException -> throw ApiException.TimeOutException(api)
                else -> throw ApiException.UnknownException(api)
            }
        }

fun Completable.checkConnectivity(connectivityService: ConnectivityService, api: Api): Completable =
    if (connectivityService.isNetworkConnection) this else Completable.error(ApiException.NoInternetConnectionException(api))

fun <T> Single<T>.checkConnectivity(connectivityService: ConnectivityService, api: Api): Single<T> =
    if (connectivityService.isNetworkConnection) this else Single.error(ApiException.NoInternetConnectionException(api))
