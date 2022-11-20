package com.example.baseandroid.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.baseandroid.networking.ApiException
import com.example.baseandroid.utils.SingleLiveEvent
import com.example.baseandroid.utils.ThrottledLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    val isLoadingSingleLive = SingleLiveEvent<Boolean>()
    val isLoading: LiveData<Boolean> = isLoadingSingleLive

    val singleLiveError = SingleLiveEvent<Throwable>()
    val error = MediatorLiveData<Throwable>()
    private val throttled = ThrottledLiveData<Throwable>(singleLiveError, 1000L) // 1s

    init {
        error.addSource(singleLiveError) {
            if (it is ApiException.RefreshTokenException) {
                isLoadingSingleLive.value = false
                error.value = it
            }
        }
        error.addSource(throttled) {
            if (it !is ApiException.RefreshTokenException) {
                isLoadingSingleLive.value = false
                error.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
