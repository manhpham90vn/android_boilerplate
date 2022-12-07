package com.manhpham.baseandroid.ui.detail

import androidx.lifecycle.MutableLiveData
import com.manhpham.baseandroid.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : BaseViewModel() {

    val text = MutableLiveData<String>()
    var img: String? = null
}
