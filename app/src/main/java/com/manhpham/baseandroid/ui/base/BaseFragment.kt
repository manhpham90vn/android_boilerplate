package com.manhpham.baseandroid.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.manhpham.baseandroid.ui.dialog.LoadingProgress

abstract class BaseFragment : Fragment() {

    val progress: LoadingProgress by lazy { LoadingProgress() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    abstract fun layoutId(): Int

    abstract fun screenType(): ScreenType
}
