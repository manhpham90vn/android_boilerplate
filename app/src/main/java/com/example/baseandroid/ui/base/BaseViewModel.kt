package com.example.baseandroid.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baseandroid.utils.SingleLiveEvent
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    val isLoading = MutableLiveData<Boolean>()

    val singleLiveError = SingleLiveEvent<Throwable>()
    val error: LiveData<Throwable> = singleLiveError

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
