package com.example.baseandroid.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.observable
import com.example.baseandroid.models.PagingUserResponse
import com.example.baseandroid.ui.home.HomePagingSource
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

interface AppPagingDataRepositoryInterface {
    fun getPagingData(): Observable<PagingData<PagingUserResponse>>
}

class AppPagingDataRepository @Inject constructor(
    private val appRemoteDataRefreshableRepositoryInterface: AppRemoteDataRefreshableRepositoryInterface
) : AppPagingDataRepositoryInterface {
    override fun getPagingData(): Observable<PagingData<PagingUserResponse>> {
        return Pager(
            config = PagingConfig(20, 10),
            pagingSourceFactory = {
                HomePagingSource(appRemoteDataRefreshableRepositoryInterface)
            }
        ).observable
    }
}
