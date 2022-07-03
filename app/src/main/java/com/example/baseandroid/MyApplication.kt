package com.example.baseandroid

import android.app.Application
import android.content.Context

class MyApplication: Application() {

    companion object {
        private lateinit var instance: MyApplication

        fun getContext(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}