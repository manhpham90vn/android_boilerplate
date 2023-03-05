package com.manhpham.baseandroid.ui.home

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.manhpham.baseandroid.models.PagingUserResponse
import com.manhpham.baseandroid.repository.AppRemoteDataRefreshableRepositoryInterface
import com.manhpham.baseandroid.usecase.PagingDataSortType
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class HomePagingSource @Inject constructor(
    private val appRemoteDataRefreshableRepositoryInterface: AppRemoteDataRefreshableRepositoryInterface,
    val type: PagingDataSortType,
) : RxPagingSource<Int, PagingUserResponse>() {
    override fun getRefreshKey(state: PagingState<Int, PagingUserResponse>): Int {
        return 1
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, PagingUserResponse>> {
        val page = params.key ?: 1
        return appRemoteDataRefreshableRepositoryInterface.getList(page, type.rawValue)
            .map { it.array.orEmpty() }
            .map<LoadResult<Int, PagingUserResponse>> {
                return@map LoadResult.Page(
                    it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1,
                )
            }
            .onErrorReturn { e ->
                return@onErrorReturn LoadResult.Error(e)
            }
    }
}
