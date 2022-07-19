package com.example.baseandroid.networking

import com.example.baseandroid.common.ConnectivityService
import com.example.baseandroid.common.SchedulerProvider
import com.example.baseandroid.models.ErrorResponse
import com.example.baseandroid.utils.applySchedulers
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 30_000L

fun <T> Single<T>.makeRequest(
    schedulerProvider: SchedulerProvider,
    connectivityService: ConnectivityService,
    gson: Gson
): Single<T> =
    applySchedulers(schedulerProvider)
        .timeout(
            TIMEOUT,
            TimeUnit.MILLISECONDS,
            Single.error(ApiException.NotRespondingException())
        )
        .checkConnectivity(connectivityService)
        .onErrorResumeNext {
            when (it) {
                is ConnectException -> throw ApiException.NoInternetConnectionException()
                is HttpException -> {
                    val adapter = gson.getAdapter(ErrorResponse::class.java)
                    try {
                        val json = adapter.fromJson(it.response()?.errorBody()?.string())
                        throw ApiException.ServerErrorException(json.code ?: "-1", json.message ?: "unknown error")
                    } catch (error: IOException) {
                        throw ApiException.ParseJSONException()
                    }
                }
                else -> throw ApiException.UnknownException()
            }
        }

fun Completable.makeRequest(
    schedulerProvider: SchedulerProvider,
    connectivityService: ConnectivityService,
    gson: Gson
): Completable =
    applySchedulers(schedulerProvider)
        .timeout(
            TIMEOUT,
            TimeUnit.MILLISECONDS,
            Completable.error(ApiException.NotRespondingException())
        )
        .checkConnectivity(connectivityService)
        .onErrorResumeNext {
            when (it) {
                is ConnectException -> throw ApiException.NoInternetConnectionException()
                is HttpException -> {
                    val adapter = gson.getAdapter(ErrorResponse::class.java)
                    try {
                        val json = adapter.fromJson(it.response()?.errorBody()?.string())
                        throw ApiException.ServerErrorException(json.code ?: "-1", json.message ?: "unknown error")
                    } catch (error: IOException) {
                        throw ApiException.ParseJSONException()
                    }
                }
                else -> throw ApiException.UnknownException()
            }
        }

fun Completable.checkConnectivity(connectivityService: ConnectivityService): Completable =
    if (connectivityService.isNetworkConnection) this else Completable.error(ApiException.NoInternetConnectionException())

fun <T> Single<T>.checkConnectivity(connectivityService: ConnectivityService): Single<T> =
    if (connectivityService.isNetworkConnection) this else Single.error(ApiException.NoInternetConnectionException())
