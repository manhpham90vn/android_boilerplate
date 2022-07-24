package com.example.baseandroid.usecase.base

import com.example.baseandroid.common.ConnectivityService
import com.example.baseandroid.common.SchedulerProvider
import com.example.baseandroid.models.ErrorResponse
import com.example.baseandroid.networking.ApiException
import com.example.baseandroid.networking.AppError
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

abstract class SingleUseCase<P, R : Any> constructor(
    open val schedulerProvider: SchedulerProvider,
    open val connectivityService: ConnectivityService,
    open val gson: Gson
) : UseCase<P, Single<R>>() {

    private val _processing = BehaviorSubject.create<Boolean>()
    val processing: Observable<Boolean> = _processing

    private val _succeeded = PublishSubject.create<R>()
    val succeeded: Observable<R> = _succeeded

    private val _failed = PublishSubject.create<Throwable>()
    val failed: Observable<Throwable> = _failed

    private val compositeDisposable = CompositeDisposable()

    abstract override fun buildUseCase(params: P): Single<R>

    override fun execute(params: P) {
        performedIfNeeded()?.let {
            buildUseCase(params)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(it)
                .addTo(compositeDisposable)
        }
    }

    open fun getErrorClassType(): Class<*> {
        return ErrorResponse::class.java
    }

    open fun handleServerError(e: Throwable) {
        val appError = e as AppError
        val throwable = appError.throwable as HttpException
        if (getErrorClassType() == ErrorResponse::class.java) {
            val adapter = gson.getAdapter(getErrorClassType())
            try {
                val json = adapter.fromJson(throwable.response()?.errorBody()?.string()) as ErrorResponse
                _failed.onNext(ApiException.ServerErrorException(json.code ?: "-1", json.message ?: "unknown error", appError.api))
            } catch (error: IOException) {
                _failed.onNext(ApiException.ParseJSONException(appError.api))
            }
        }
    }

    fun onCleared() {
        compositeDisposable.clear()
    }

    private fun performedIfNeeded(): DisposableSingleObserver<R>? {
        if (_processing.value == true) {
            _failed.onNext(ApiException.ActionAlreadyPerformingException)
            return null
        }
        if (!connectivityService.isNetworkConnection) {
            _failed.onNext(ApiException.NoInternetConnectionException)
            return null
        }
        _processing.onNext(true)
        return object : DisposableSingleObserver<R>() {
            override fun onSuccess(t: R) {
                _processing.onNext(false)
                _succeeded.onNext(t)
            }

            override fun onError(e: Throwable) {
                val appError = e as AppError
                when (appError.throwable) {
                    is HttpException -> {
                        handleServerError(appError)
                    }
                    is ConnectException -> _failed.onNext(ApiException.NoInternetConnectionException)
                    is TimeoutException -> _failed.onNext(ApiException.TimeOutException(appError.api))
                    is SocketTimeoutException -> _failed.onNext(ApiException.TimeOutException(appError.api))
                    is ApiException.RefreshTokenException -> _failed.onNext(ApiException.RefreshTokenException)
                    else -> _failed.onNext(ApiException.UnknownException(appError.api))
                }
                _processing.onNext(false)
            }
        }
    }
}
