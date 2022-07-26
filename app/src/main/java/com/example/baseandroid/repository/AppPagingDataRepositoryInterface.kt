package com.example.baseandroid.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.observable
import com.example.baseandroid.models.PagingUserResponse
import com.example.baseandroid.ui.home.HomePagingSourceAscending
import com.example.baseandroid.ui.home.HomePagingSourceDescending
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

interface AppPagingDataRepositoryInterface {
    fun getPagingDataAscending(): Observable<PagingData<PagingUserResponse>>
    fun getPagingDataDescending(): Observable<PagingData<PagingUserResponse>>
}

class AppPagingDataRepository @Inject constructor(
    private val appRemoteDataRefreshableRepositoryInterface: AppRemoteDataRefreshableRepositoryInterface
) : AppPagingDataRepositoryInterface {
    override fun getPagingDataAscending(): Observable<PagingData<PagingUserResponse>> {
        return Pager(
            config = PagingConfig(20, 10),
            pagingSourceFactory = {
                HomePagingSourceAscending(appRemoteDataRefreshableRepositoryInterface)
            }
        ).observable
    }

    override fun getPagingDataDescending(): Observable<PagingData<PagingUserResponse>> {
        return Pager(
            config = PagingConfig(20, 10),
            pagingSourceFactory = {
                HomePagingSourceDescending(appRemoteDataRefreshableRepositoryInterface)
            }
        ).observable
    }
}
