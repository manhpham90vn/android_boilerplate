package com.example.baseandroid.usecase

import com.example.baseandroid.models.PagingResponse
import com.example.baseandroid.models.request.LoadMoreRequest
import com.example.baseandroid.models.request.PagingDataSortType
import com.example.baseandroid.repository.AppRemoteDataRefreshableRepositoryInterface
import com.example.baseandroid.usecase.base.SingleUseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetListUserUseCase @Inject constructor(
    private val appRemoteDataRefreshableRepositoryInterface: AppRemoteDataRefreshableRepositoryInterface,
) : SingleUseCase<LoadMoreRequest, PagingResponse>() {
    override fun buildUseCase(loadMoreRequestParam: LoadMoreRequest): Single<PagingResponse> {
        return appRemoteDataRefreshableRepositoryInterface.getList(
            loadMoreRequestParam.page,
            loadMoreRequestParam.perPage,
            loadMoreRequestParam.pagingDataSortType?.rawValue ?: PagingDataSortType.ASCENDING.rawValue
        )
    }
}