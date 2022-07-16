package com.example.baseandroid.ui.base

import com.example.baseandroid.ui.dialog.LoadingProgress
import dagger.android.support.DaggerFragment

open class BaseFragment: DaggerFragment() {

    val progress: LoadingProgress by lazy { LoadingProgress() }

}