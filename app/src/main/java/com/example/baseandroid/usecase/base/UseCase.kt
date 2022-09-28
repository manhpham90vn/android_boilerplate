package com.example.baseandroid.usecase.base

abstract class UseCase<in P, out R> {
    var input: @UnsafeVariance P? = null

    abstract fun buildUseCase(params: P): R
    open fun execute(params: P) {
        input = params
        buildUseCase(params)
    }
    fun retry() {
        if (input != null) {
            execute(input!!)
        }
    }
}