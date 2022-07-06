package com.example.baseandroid.ui.base

import android.app.Activity
import android.util.Log

open class BaseActivity: Activity() {

    companion object {
        val TAG = BaseActivity::class.java.simpleName
        fun log(message: String) {
            Log.d(TAG, message)
        }
    }
}