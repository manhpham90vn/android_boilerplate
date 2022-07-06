package com.example.baseandroid.ui.base

import android.app.Activity
import android.util.Log

open class BaseActivity: Activity() {
    fun log(message: String) {
        Log.d(this.javaClass.simpleName, message)
    }
}