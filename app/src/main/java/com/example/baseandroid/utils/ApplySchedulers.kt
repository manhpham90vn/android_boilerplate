package com.example.baseandroid.utils

import com.example.baseandroid.common.SchedulerProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

fun <T> Single<T>.applySchedulers(schedulerProvider: SchedulerProvider): Single<T> {
    return subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())
}

fun Completable.applySchedulers(schedulerProvider: SchedulerProvider): Completable {
    return subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())
}
