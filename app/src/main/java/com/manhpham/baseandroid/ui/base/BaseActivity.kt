package com.manhpham.baseandroid.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.manhpham.baseandroid.ui.dialog.LoadingProgress

abstract class BaseActivity : AppCompatActivity() {

    val progress: LoadingProgress by lazy { LoadingProgress() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
    }

    abstract fun layoutId(): Int
}
