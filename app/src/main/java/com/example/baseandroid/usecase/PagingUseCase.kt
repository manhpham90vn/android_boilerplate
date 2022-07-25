package com.example.baseandroid.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.observable
import com.example.baseandroid.common.ConnectivityService
import com.example.baseandroid.common.SchedulerProvider
import com.example.baseandroid.models.PagingUserResponse
import com.example.baseandroid.ui.home.HomePagingSource
import com.example.baseandroid.usecase.base.ObservableUseCase
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class PagingUseCase @Inject constructor(
    private val pagingDataUseCase: PagingDataUseCase,
    schedulerProvider: SchedulerProvider,
    connectivityService: ConnectivityService,
    gson: Gson
) : ObservableUseCase<Unit, PagingData<PagingUserResponse>>(schedulerProvider, connectivityService, gson) {
    override fun buildUseCase(params: Unit): Observable<PagingData<PagingUserResponse>> {
        return Pager(
            config = PagingConfig(20, 10),
            pagingSourceFactory = {
                HomePagingSource(pagingDataUseCase)
            }
        ).observable
    }
}
