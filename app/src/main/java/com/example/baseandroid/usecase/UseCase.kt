package com.example.baseandroid.usecase

abstract class UseCase<in P, out R> {
    abstract fun buildUseCase(params: P): R
    abstract fun execute(params: P)
}
