package com.example.baseandroid.ui.home

import android.os.Handler
import android.os.Looper
import com.example.baseandroid.models.PagingResponse
import com.example.baseandroid.models.UserResponse
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.repository.AppRemoteDataRefreshableRepositoryInterface
import com.example.baseandroid.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor(): BaseViewModel() {

    @Inject lateinit var appRemoteDataRefreshableRepositoryInterface: AppRemoteDataRefreshableRepositoryInterface
    @Inject lateinit var appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface

    fun callApi() {
        Handler(Looper.getMainLooper()).postDelayed({
            appRemoteDataRefreshableRepositoryInterface.getUserInfo().enqueue(object:
                Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    Timber.d(response.body()?.email ?: "getUserInfo error")
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Timber.d("refresh onFailure")
                }
            })
        }, 500)

        Handler(Looper.getMainLooper()).postDelayed({
            appRemoteDataRefreshableRepositoryInterface.getUserInfo().enqueue(object:
                Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    Timber.d(response.body()?.email ?: "getUserInfo error")
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Timber.d("refresh onFailure")
                }
            })
        }, 1000)

        Handler(Looper.getMainLooper()).postDelayed({
            appRemoteDataRefreshableRepositoryInterface.getUserInfo().enqueue(object:
                Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    Timber.d(response.body()?.email ?: "getUserInfo error")
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Timber.d("refresh onFailure")
                }
            })
        }, 1500)

        Handler(Looper.getMainLooper()).postDelayed({
            appRemoteDataRefreshableRepositoryInterface.getUserInfo().enqueue(object:
                Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    Timber.d(response.body()?.email ?: "getUserInfo error")
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Timber.d("refresh onFailure")
                }
            })
        }, 2000)

        Handler(Looper.getMainLooper()).postDelayed({
            appRemoteDataRefreshableRepositoryInterface.getUserInfo().enqueue(object:
                Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    Timber.d(response.body()?.email ?: "getUserInfo error")
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Timber.d("refresh onFailure")
                }
            })
        }, 2500)

        // call at the same time
        appRemoteDataRefreshableRepositoryInterface.getUserInfo().enqueue(object:
            Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                Timber.d(response.body()?.email ?: "getUserInfo error")
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Timber.d("refresh onFailure")
            }
        })

        appRemoteDataRefreshableRepositoryInterface.getUserInfo().enqueue(object:
            Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                Timber.d(response.body()?.email ?: "getUserInfo error")
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Timber.d("refresh onFailure")
            }
        })

        appRemoteDataRefreshableRepositoryInterface.getList(1).enqueue(object:
            Callback<PagingResponse> {
            override fun onResponse(
                call: Call<PagingResponse>,
                response: Response<PagingResponse>
            ) {
                Timber.d(response.body()?.array?.first()?.name ?: "getList error")
            }

            override fun onFailure(call: Call<PagingResponse>, t: Throwable) {
                Timber.d("getList onFailure")
            }
        })

        appRemoteDataRefreshableRepositoryInterface.getList(1).enqueue(object:
            Callback<PagingResponse> {
            override fun onResponse(
                call: Call<PagingResponse>,
                response: Response<PagingResponse>
            ) {
                Timber.d(response.body()?.array?.first()?.name ?: "getList error")
            }

            override fun onFailure(call: Call<PagingResponse>, t: Throwable) {
                Timber.d("getList onFailure")
            }
        })
    }

    fun cleanData() {
        appLocalDataRepositoryInterface.cleanRefreshToken()
        appLocalDataRepositoryInterface.cleanToken()
    }

}