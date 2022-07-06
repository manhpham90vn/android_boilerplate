package com.example.baseandroid.ui.login

import android.app.Application
import com.example.baseandroid.models.LoginResponse
import com.example.baseandroid.ui.MyApplication
import com.example.baseandroid.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val application: Application): BaseViewModel(), Callback<LoginResponse> {

    private var callback: ((Boolean) -> Unit)? = null

    fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        this.callback = callback
        (application as MyApplication).appRemoteData.callLogin(email, password).enqueue(this)
    }

    fun isLogin(): Boolean {
        return (application as MyApplication).appLocalData.isLogin()
    }

    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
        (application as MyApplication).appLocalData.setToken(response.body()?.token ?: "")
        application.appLocalData.setToken(response.body()?.refreshToken ?: "")
        callback?.invoke(true)
    }

    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
        callback?.invoke(true)
    }

}