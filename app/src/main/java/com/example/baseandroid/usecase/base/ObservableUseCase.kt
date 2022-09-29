package com.example.baseandroid.usecase.base

import com.example.baseandroid.data.remote.Api
import com.example.baseandroid.networking.ApiException
import com.example.baseandroid.networking.AppError
import com.example.baseandroid.service.ConnectivityService
import com.example.baseandroid.service.SchedulerProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

abstract class ObservableUseCase<P, R : Any>: UseCase<P, Observable<R>>() {

    @Inject
    lateinit var schedulerProvider: SchedulerProvider
    @Inject
    lateinit var connectivityService: ConnectivityService

    private val _processing = BehaviorSubject.create<Boolean>()
    val processing: Observable<Boolean> = _processing

    private val _succeeded = PublishSubject.create<R>()
    val succeeded: Observable<R> = _succeeded

    private val _failed = PublishSubject.create<Throwable>()
    val failed: Observable<Throwable> = _failed

    val compositeDisposable = CompositeDisposable()

    abstract override fun buildUseCase(params: P): Observable<R>

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

    private fun performedIfNeeded(): DisposableObserver<R>? {
        if (_processing.value == true) {
            _failed.onNext(AppError(Api.None, ApiException.ActionAlreadyPerformingException))
            return null
        }
        if (!connectivityService.isNetworkConnection) {
            _failed.onNext(AppError(Api.None, ApiException.NoInternetConnectionException))
            return null
        }
        _processing.onNext(true)
        return object : DisposableObserver<R>() {
            override fun onNext(t: R) {
                _succeeded.onNext(t)
                _processing.onNext(false)
            }

            override fun onError(e: Throwable) {
                _failed.onNext(e)
                _processing.onNext(false)
            }

            override fun onComplete() {
                Timber.d("onComplete")
            }
        }
    }
}
