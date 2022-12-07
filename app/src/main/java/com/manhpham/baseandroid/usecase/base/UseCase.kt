package com.manhpham.baseandroid.usecase.base

import java.util.concurrent.atomic.AtomicReference

abstract class UseCase<in P, out R> {
    var cacheParams: AtomicReference<@UnsafeVariance P?> = AtomicReference(null)

    abstract fun buildUseCase(params: P): R

    open fun execute(params: P) {
        cacheParams.set(params)
        buildUseCase(params)
    }

    fun retry() {
        cacheParams.get()?.let {
            execute(it)
        }
    }
}