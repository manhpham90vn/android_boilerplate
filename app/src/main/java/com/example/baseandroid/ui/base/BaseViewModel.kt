package com.example.baseandroid.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {
    fun log(message: String) {
        Log.d(this.javaClass.simpleName, message)
    }
}