package com.example.baseandroid.ui.home

import android.app.Application
import android.os.Handler
import android.os.Looper
import com.example.baseandroid.models.PagingResponse
import com.example.baseandroid.models.UserResponse
import com.example.baseandroid.ui.MyApplication
import com.example.baseandroid.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val application: Application): BaseViewModel() {

    fun callApi() {
        Handler(Looper.getMainLooper()).postDelayed({
            (application as MyApplication).appRemoteData.getUserInfo().enqueue(object:
                Callback<UserResponse> {
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
        }, 500)

        Handler(Looper.getMainLooper()).postDelayed({
            (application as MyApplication).appRemoteData.getUserInfo().enqueue(object:
                Callback<UserResponse> {
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
        }, 1000)

        Handler(Looper.getMainLooper()).postDelayed({
            (application as MyApplication).appRemoteData.getUserInfo().enqueue(object:
                Callback<UserResponse> {
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
        }, 1500)

        Handler(Looper.getMainLooper()).postDelayed({
            (application as MyApplication).appRemoteData.getUserInfo().enqueue(object:
                Callback<UserResponse> {
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
        }, 2000)

        Handler(Looper.getMainLooper()).postDelayed({
            (application as MyApplication).appRemoteData.getUserInfo().enqueue(object:
                Callback<UserResponse> {
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
        }, 2500)

        // call at the same time
        (application as MyApplication).appRemoteData.getUserInfo().enqueue(object:
            Callback<UserResponse> {
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

        application.appRemoteData.getUserInfo().enqueue(object:
            Callback<UserResponse> {
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

        application.appRemoteData.getList(1).enqueue(object:
            Callback<PagingResponse> {
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

        application.appRemoteData.getList(1).enqueue(object:
            Callback<PagingResponse> {
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