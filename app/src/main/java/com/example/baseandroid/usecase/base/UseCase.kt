package com.example.baseandroid.usecase.base

abstract class UseCase<in P, out R> {
    abstract fun buildUseCase(params: P): R
    abstract fun execute(params: P)
}
