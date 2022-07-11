package com.example.baseandroid.ui.login

import androidx.lifecycle.MutableLiveData
import com.example.baseandroid.models.LoginResponse
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.repository.AppRemoteDataRepositoryInterface
import com.example.baseandroid.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel @Inject constructor(): BaseViewModel(), Callback<LoginResponse> {

    @Inject lateinit var appRemoteDataRepositoryInterface: AppRemoteDataRepositoryInterface
    @Inject lateinit var appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private var callback: ((Boolean) -> Unit)? = null

    fun login(callback: (Boolean) -> Unit) {
        this.callback = callback
        email.value = "admin@admin.com"
        password.value = "pwd12345"
        appRemoteDataRepositoryInterface.callLogin(email.value.orEmpty(), password.value.orEmpty()).enqueue(this)
    }

    fun isLogin(): Boolean {
        return appLocalDataRepositoryInterface.isLogin()
    }

    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
        val token = response.body()?.token ?: ""
        val refreshToken = response.body()?.refreshToken ?: ""
        if (token.isNotEmpty() && refreshToken.isNotEmpty()) {
            appLocalDataRepositoryInterface.setToken(token)
            appLocalDataRepositoryInterface.setRefreshToken(refreshToken)
            callback?.invoke(true)
        } else {
            callback?.invoke(false)
        }
    }

    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
        Timber.d(t.localizedMessage ?: "")
        callback?.invoke(false)
    }

}