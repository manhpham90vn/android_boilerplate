package com.example.baseandroid.ui.base

import android.util.Log

open class BaseViewModel {

    companion object {
        val TAG = BaseViewModel::class.java.simpleName
        fun log(message: String) {
            Log.d(TAG, message)
        }
    }

}