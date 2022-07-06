package com.example.baseandroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.baseandroid.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : Activity(), Callback<LoginResponse> {

    companion object {
        val TAG = LoginActivity::class.java.simpleName
        fun log(message: String) {
            Log.d(TAG, message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.button)

        loginButton.setOnClickListener {
            log("start login")
            (application as MyApplication).appRemoteData.callLogin(email.text.toString(), password.text.toString()).enqueue(this)
        }

        if ((application as MyApplication).appLocalData.isLogin()) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

        // save data
        (application as MyApplication).appLocalData.setToken(response.body()?.token ?: "")
        (application as MyApplication).appLocalData.setRefreshToken(response.body()?.refreshToken ?: "")

        log("login success")

        // navigation
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
        log("login onFailure")
    }
}