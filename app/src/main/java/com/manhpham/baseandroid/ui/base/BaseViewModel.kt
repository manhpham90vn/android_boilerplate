package com.manhpham.baseandroid.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.manhpham.baseandroid.utils.SingleLiveEvent
import com.manhpham.baseandroid.utils.ThrottledLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    val isLoadingSingleLive = SingleLiveEvent<Boolean>()
    val isLoading: LiveData<Boolean> = isLoadingSingleLive

    val singleLiveError = SingleLiveEvent<Throwable>()
    val throttledError = ThrottledLiveData<Throwable>(singleLiveError, 1000L) // 1s

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
