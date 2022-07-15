package com.example.baseandroid.ui.detail

import androidx.lifecycle.MutableLiveData
import com.example.baseandroid.ui.base.BaseViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor() : BaseViewModel() {

    val text = MutableLiveData<String>()

}