package com.example.baseandroid.ui.base

import android.os.Bundle
import com.example.baseandroid.ui.dialog.LoadingProgress
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    val progress: LoadingProgress by lazy { LoadingProgress() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
    }

    abstract fun layoutId(): Int
}
