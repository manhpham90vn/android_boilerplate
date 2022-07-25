package com.example.baseandroid.ui.home

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.example.baseandroid.models.PagingUserResponse
import com.example.baseandroid.usecase.PagingDataUseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class HomePagingSource @Inject constructor(private val pagingDataUseCase: PagingDataUseCase) : RxPagingSource<Int, PagingUserResponse>() {
    override fun getRefreshKey(state: PagingState<Int, PagingUserResponse>): Int {
        return 1
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, PagingUserResponse>> {
        val page = params.key ?: 1
        return pagingDataUseCase.getUseCase(page)
            .map {
                return@map LoadResult.Page(
                    it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            }
    }
}
