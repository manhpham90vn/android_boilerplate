package com.example.baseandroid.models.request

import com.example.baseandroid.extensions.checkCanLoadMore
import javax.inject.Inject

data class LoadMoreRequest(
    val page: Int,
    val perPage: Int? = null,
    val pagingDataSortType: PagingDataSortType? = PagingDataSortType.ASCENDING
)

class DataLoadMore <T> @Inject constructor(): DataLoadMoreInterface<T> {
    override var pageLoadMore: Int = 1
    override var canLoadMore = true
    override var isLoadingLoadMore = false
    override var perPageLoadMore = 20

    override fun checkCallApiMore(): Boolean {
        return if (isLoadingLoadMore || !canLoadMore) {
            false
        } else {
            isLoadingLoadMore = true
            true
        }
    }

    override fun handleDataWhenCallApiSuccess(listT: List<T>?) {
        pageLoadMore++
        canLoadMore = listT.checkCanLoadMore(perPageLoadMore)
        isLoadingLoadMore = false
    }

    override fun clearDataLoadMore() {
        pageLoadMore = 1
        canLoadMore = true
        isLoadingLoadMore = false
    }
}

interface DataLoadMoreInterface<T> {
    var pageLoadMore: Int
    var canLoadMore: Boolean
    var isLoadingLoadMore: Boolean
    var perPageLoadMore: Int

    fun checkCallApiMore(): Boolean
    fun clearDataLoadMore()
    fun handleDataWhenCallApiSuccess(listT: List<T>?)
}

enum class PagingDataSortType(val rawValue: String) {
    ASCENDING("ascending"),
    DESCENDING("descending")
}