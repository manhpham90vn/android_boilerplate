package com.example.baseandroid.ui.login

import androidx.lifecycle.MutableLiveData
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.repository.AppRemoteDataRepositoryInterface
import com.example.baseandroid.ui.base.BaseViewModel
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

class LoginViewModel @Inject constructor(): BaseViewModel() {

    @Inject lateinit var appRemoteDataRepositoryInterface: AppRemoteDataRepositoryInterface
    @Inject lateinit var appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val welcomeString = MutableLiveData<String>()

    fun login(callback: (Boolean) -> Unit) {
        email.value = "admin@admin.com"
        welcomeString.value = "Welcome admin@admin.com"
        password.value = "pwd12345"
        appRemoteDataRepositoryInterface.callLogin(email.value.orEmpty(), password.value.orEmpty())
            .doOnSuccess {
                if (!it.token.isNullOrEmpty() && !it.refreshToken.isNullOrEmpty()) {
                    appLocalDataRepositoryInterface.setToken(it.token)
                    appLocalDataRepositoryInterface.setRefreshToken(it.refreshToken)
                    callback.invoke(true)
                } else {
                    callback.invoke(false)
                }
            }
            .subscribe()
            .addTo(compositeDisposable)
    }

    fun isLogin(): Boolean {
        return appLocalDataRepositoryInterface.isLogin()
    }
}