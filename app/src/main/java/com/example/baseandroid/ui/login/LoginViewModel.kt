package com.example.baseandroid.ui.login

import com.example.baseandroid.models.LoginResponse
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.repository.AppRemoteDataRepository
import com.example.baseandroid.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginViewModel @Inject constructor(): BaseViewModel(), Callback<LoginResponse> {

    @Inject lateinit var appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface
    @Inject lateinit var appRemoteDataRepository: AppRemoteDataRepository

    private var callback: ((Boolean) -> Unit)? = null

    fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        this.callback = callback
        appRemoteDataRepository.callLogin(email, password).enqueue(this)
    }

    fun isLogin(): Boolean {
        return appLocalDataRepositoryInterface.isLogin()
    }

    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
        appLocalDataRepositoryInterface.setToken(response.body()?.token ?: "")
        appLocalDataRepositoryInterface.setRefreshToken(response.body()?.refreshToken ?: "")
        callback?.invoke(true)
    }

    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
        callback?.invoke(true)
    }

}