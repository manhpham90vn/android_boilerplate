package com.example.baseandroid.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.baseandroid.ui.dialog.LoadingProgress
import dagger.android.support.DaggerFragment

abstract class BaseFragment: DaggerFragment() {

    val progress: LoadingProgress by lazy { LoadingProgress() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    abstract fun layoutId(): Int

}