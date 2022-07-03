package com.example.baseandroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.baseandroid.data.LocalStorage
import com.example.baseandroid.models.LoginResponse
import com.example.baseandroid.networking.NetworkModule
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
            NetworkModule.provideAppApi().callLogin(email.text.toString(), password.text.toString()).enqueue(this)
        }

        if (LocalStorage.get(LocalStorage.Constants.token) != null && LocalStorage.get(LocalStorage.Constants.refreshToken) != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

        // save data
        LocalStorage.save(LocalStorage.Constants.token, response.body()!!.token!!)
        LocalStorage.save(LocalStorage.Constants.refreshToken, response.body()!!.refreshToken!!)

        log("login success")

        // navigation
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
        log("login onFailure")
    }
}