package com.example.baseandroid

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.baseandroid.data.LocalStorage
import com.example.baseandroid.models.PagingResponse
import com.example.baseandroid.models.UserResponse
import com.example.baseandroid.networking.NetworkModule
import com.example.baseandroid.networking.RefreshTokenAuthenticator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : Activity() {

    private lateinit var local: LocalStorage

    companion object {
        val TAG = HomeActivity::class.java.simpleName
        fun log(message: String) {
            Log.d(TAG, message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        local = LocalStorage(this)

        val logout = findViewById<Button>(R.id.logout)
        val refresh = findViewById<Button>(R.id.refresh)

        logout.setOnClickListener {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
            local.clear(LocalStorage.token)
            local.clear(LocalStorage.refreshToken)
            finish()
        }

        refresh.setOnClickListener {
            Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show()
            NetworkModule(this).provideAppApi().getUserInfo().enqueue(object: Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    log(response.body()?.email ?: "getUserInfo error")
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    log("refresh onFailure")
                }
            })
            NetworkModule(this).provideAppApi().getList(1).enqueue(object: Callback<PagingResponse> {
                override fun onResponse(
                    call: Call<PagingResponse>,
                    response: Response<PagingResponse>
                ) {
                    log(response.body()?.array?.first()?.name ?: "getList error")
                }

                override fun onFailure(call: Call<PagingResponse>, t: Throwable) {
                    log("getList onFailure")
                }
            })
        }
    }

}