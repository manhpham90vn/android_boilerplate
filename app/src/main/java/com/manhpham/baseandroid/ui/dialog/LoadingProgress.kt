package com.manhpham.baseandroid.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.manhpham.baseandroid.R

class LoadingProgress : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.loading_progress, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        return super.onCreateDialog(savedInstanceState)
    }

    fun showLoadingProgress(activity: AppCompatActivity) {
        activity.run {
            startLoading(this.supportFragmentManager)
        }
    }

    fun hideLoadingProgress(activity: AppCompatActivity) {
        activity.run {
            stopLoading(this.supportFragmentManager)
        }
    }

    private fun startLoading(fragmentManager: FragmentManager) {
        if (isAdded || fragmentManager.isStateSaved || fragmentManager.isDestroyed) {
            return
        }
        show(fragmentManager, this::class.java.name)
        fragmentManager.executePendingTransactions()
    }

    private fun stopLoading(fragmentManager: FragmentManager) {
        fragmentManager.fragments.forEach {
            if (it is LoadingProgress) {
                it.dismiss()
            }
        }
    }
}
