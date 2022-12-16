package com.manhpham.baseandroid.usecase.base

import com.manhpham.baseandroid.data.remote.Api
import com.manhpham.baseandroid.networking.ApiException
import com.manhpham.baseandroid.networking.AppError
import com.manhpham.baseandroid.service.ConnectivityService
import com.manhpham.baseandroid.service.SchedulerProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.observers.DisposableCompletableObserver
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

abstract class CompletableUseCase<P : Any> : UseCase<P, Completable>() {

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @Inject
    lateinit var connectivityService: ConnectivityService

    private val _processing = BehaviorSubject.create<Boolean>()
    val processing: Observable<Boolean> = _processing

    private val _complete = PublishSubject.create<Unit>()
    val complete: Observable<Unit> = _complete

    private val _failed = PublishSubject.create<Throwable>()
    val failed: Observable<Throwable> = _failed

    private val compositeDisposable = CompositeDisposable()

    abstract override fun buildUseCase(params: P): Completable

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

    private fun performedIfNeeded(): DisposableCompletableObserver? {
        if (_processing.value == true) {
            _failed.onNext(AppError(Api.None, ApiException.ActionAlreadyPerformingException))
            return null
        }
        if (!connectivityService.isNetworkConnection) {
            _failed.onNext(AppError(Api.None, ApiException.NoInternetConnectionException))
            return null
        }
        _processing.onNext(true)
        return object : DisposableCompletableObserver() {
            override fun onComplete() {
                _complete.onNext(Unit)
                _processing.onNext(false)
            }

            override fun onError(e: Throwable) {
                _failed.onNext(e)
                _processing.onNext(false)
            }
        }
    }
}
