package com.example.baseandroid.repository

import com.example.baseandroid.data.remote.ApiClientRefreshable
import com.example.baseandroid.models.PagingResponse
import com.example.baseandroid.models.UserResponse
import com.example.baseandroid.networking.RefreshTokenException
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

interface AppRemoteDataRefreshableRepositoryInterface {
    fun getUserInfo(): Single<UserResponse>
    fun getList(page: Int): Single<PagingResponse>

}
class AppRemoteDataRefreshableRepository @Inject constructor(private val apiClientRefreshable: ApiClientRefreshable): AppRemoteDataRefreshableRepositoryInterface {

    override fun getUserInfo(): Single<UserResponse> {
        return apiClientRefreshable.getUserInfo()
            .onErrorResumeNext {
                when (it) {
                    is RefreshTokenException -> throw it
                    else -> return@onErrorResumeNext Single.just(UserResponse())
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getList(page: Int): Single<PagingResponse> {
        return apiClientRefreshable.getList(page)
            .onErrorResumeNext {
                when (it) {
                    is RefreshTokenException -> throw it
                    else -> return@onErrorResumeNext Single.just(PagingResponse())
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}