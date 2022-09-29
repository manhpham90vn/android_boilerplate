package com.example.baseandroid.usecase

import androidx.paging.PagingData
import com.example.baseandroid.models.PagingUserResponse
import com.example.baseandroid.repository.AppPagingDataRepositoryInterface
import com.example.baseandroid.usecase.base.ObservableUseCase
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

enum class PagingDataSortType(val rawValue: String) {
    ASCENDING("ascending"),
    DESCENDING("descending")
}

class PagingDataUseCase @Inject constructor(
    private val appPagingDataRepositoryInterface: AppPagingDataRepositoryInterface,
) : ObservableUseCase<PagingDataSortType, PagingData<PagingUserResponse>>() {
    override fun buildUseCase(params: PagingDataSortType): Observable<PagingData<PagingUserResponse>> {
        return appPagingDataRepositoryInterface.getPagingData(params)
    }
}
