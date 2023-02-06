package com.manhpham.baseandroid.usecase // ktlint-disable filename

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

class Recorder<P : Any> {

    private val compositeDisposable = CompositeDisposable()
    val result = arrayListOf<P>()

    fun onNext(valueSubject: Observable<P>) {
        valueSubject
            .subscribe {
                result.add(it)
            }
            .addTo(compositeDisposable)
    }
}
