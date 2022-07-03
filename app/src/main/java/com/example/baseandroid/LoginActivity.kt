package com.example.baseandroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.baseandroid.data.LocalStorage
import com.example.baseandroid.models.LoginResponse
import com.example.baseandroid.networking.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : Activity(), Callback<LoginResponse> {

    private lateinit var local: LocalStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        local = LocalStorage(this)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.button)

        loginButton.setOnClickListener {
            NetworkModule(this).provideAppApi().callLogin(email.text.toString(), password.text.toString()).enqueue(this)
        }

        if (local.get(LocalStorage.token) != null && local.get(LocalStorage.refreshToken) != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

        // save data
        local.save(LocalStorage.token, response.body()!!.token!!)
        local.save(LocalStorage.refreshToken, response.body()!!.refreshToken!!)

        // show alert
        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()

        // navigation
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
        Toast.makeText(this, "Login Error ${t.toString()}", Toast.LENGTH_SHORT).show()
    }
}