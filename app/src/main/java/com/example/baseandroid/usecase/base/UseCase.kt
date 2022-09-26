package com.example.baseandroid.usecase.base

abstract class UseCase<in P, out R> {
    abstract fun buildUseCase(params: P): R
    open fun execute(params: P) {
        buildUseCase(params)
    }
}