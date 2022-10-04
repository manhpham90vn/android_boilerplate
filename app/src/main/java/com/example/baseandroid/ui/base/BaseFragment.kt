package com.example.baseandroid.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.baseandroid.ui.dialog.LoadingProgress

abstract class BaseFragment : Fragment() {

    val progress: LoadingProgress by lazy { LoadingProgress() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewAndData()
        subscribeData()
    }

    abstract fun layoutId(): Int
    abstract fun screenType(): ScreenType
    abstract fun initViewAndData()
    abstract fun subscribeData()
}
