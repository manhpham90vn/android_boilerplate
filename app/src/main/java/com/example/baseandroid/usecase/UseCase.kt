package com.example.baseandroid.usecase

import com.example.baseandroid.common.ConnectivityService
import com.example.baseandroid.common.SchedulerProvider
import com.example.baseandroid.data.remote.Api
import com.example.baseandroid.models.ErrorResponse
import com.example.baseandroid.networking.ApiException
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

abstract class SingleUseCase<P, R> constructor(
    open val schedulerProvider: SchedulerProvider,
    open val connectivityService: ConnectivityService,
    open val gson: Gson
) {

    private val _processing = BehaviorSubject.create<Boolean>()
    val processing: Observable<Boolean> = _processing

    private val _succeeded = PublishSubject.create<R>()
    val succeeded: Observable<R> = _succeeded

    private val _failed = PublishSubject.create<Throwable>()
    val failed: Observable<Throwable> = _failed

    abstract fun buildUseCase(params: P): Single<R>

    abstract fun getApiName(): Api

    fun execute(params: P, compositeDisposable: CompositeDisposable) {
        singleObserverIfNotPerformed()?.let {
            buildUseCase(params)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(it)
                .addTo(compositeDisposable)
        }
    }

    private fun singleObserverIfNotPerformed(): DisposableSingleObserver<R>? {
        if (_processing.value == true) {
            _failed.onNext(ApiException.ActionAlreadyPerformingException(getApiName()))
            return null
        }
        if (!connectivityService.isNetworkConnection) {
            _failed.onNext(ApiException.NoInternetConnectionException(getApiName()))
            return null
        }
        _processing.onNext(true)
        return object : DisposableSingleObserver<R>() {
            override fun onSuccess(t: R) {
                _processing.onNext(false)
                _succeeded.onNext(t)
            }

            override fun onError(e: Throwable) {
                when (e) {
                    is HttpException -> {
                        val adapter = gson.getAdapter(ErrorResponse::class.java)
                        try {
                            val json = adapter.fromJson(e.response()?.errorBody()?.string())
                            _failed.onNext(ApiException.ServerErrorException(json.code ?: "-1", json.message ?: "unknown error", getApiName()))
                        } catch (error: IOException) {
                            _failed.onNext(ApiException.ParseJSONException(getApiName()))
                        }
                    }
                    is ConnectException -> _failed.onNext(ApiException.NoInternetConnectionException(getApiName()))
                    is TimeoutException -> _failed.onNext(ApiException.TimeOutException(getApiName()))
                    is SocketTimeoutException -> _failed.onNext(ApiException.TimeOutException(getApiName()))
                    is ApiException.RefreshTokenException -> _failed.onNext(ApiException.RefreshTokenException)
                    else -> _failed.onNext(ApiException.UnknownException(getApiName()))
                }
                _processing.onNext(false)
            }
        }
    }
}
