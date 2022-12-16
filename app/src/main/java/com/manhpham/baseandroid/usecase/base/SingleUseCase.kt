package com.manhpham.baseandroid.usecase.base

import com.manhpham.baseandroid.data.remote.Api
import com.manhpham.baseandroid.networking.ApiException
import com.manhpham.baseandroid.networking.AppError
import com.manhpham.baseandroid.service.ConnectivityService
import com.manhpham.baseandroid.service.SchedulerProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

abstract class SingleUseCase<P, R : Any> : UseCase<P, Single<R>>() {

    @Inject lateinit var schedulerProvider: SchedulerProvider

    @Inject lateinit var connectivityService: ConnectivityService

    private val _processing = BehaviorSubject.create<Boolean>()
    val processing: Observable<Boolean> = _processing

    private val _succeeded = PublishSubject.create<R>()
    val succeeded: Observable<R> = _succeeded

    private val _failed = PublishSubject.create<Throwable>()
    val failed: Observable<Throwable> = _failed

    private val compositeDisposable = CompositeDisposable()

    abstract override fun buildUseCase(params: P): Single<R>

    override fun execute(params: P) {
        cacheParams.set(params)
        performedIfNeeded()?.let {
            buildUseCase(params)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(it)
                .addTo(compositeDisposable)
        }
    }

    fun onCleared() {
        compositeDisposable.clear()
    }

    private fun performedIfNeeded(): DisposableSingleObserver<R>? {
        if (_processing.value == true) {
            _failed.onNext(AppError(Api.None, ApiException.ActionAlreadyPerformingException))
            return null
        }
        if (!connectivityService.isNetworkConnection) {
            _failed.onNext(AppError(Api.None, ApiException.NoInternetConnectionException))
            return null
        }
        _processing.onNext(true)
        return object : DisposableSingleObserver<R>() {
            override fun onSuccess(t: R) {
                _succeeded.onNext(t)
                _processing.onNext(false)
            }

            override fun onError(e: Throwable) {
                _failed.onNext(e)
                _processing.onNext(false)
            }
        }
    }
}
