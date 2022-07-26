package com.example.baseandroid.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.baseandroid.R
import com.example.baseandroid.databinding.ActivityHomeBinding
import com.example.baseandroid.di.ViewModelFactory
import com.example.baseandroid.networking.ApiErrorHandler
import com.example.baseandroid.ui.base.BaseActivity
import com.example.baseandroid.ui.detail.DetailActivity
import com.wada811.databinding.withBinding
import timber.log.Timber
import javax.inject.Inject

interface HomeHandler {
    fun didTapLogOut()
    fun didTapRefresh()
    fun didTapSort()
}

class HomeActivity : BaseActivity(), HomeHandler {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<HomeViewModel>
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    @Inject lateinit var errorHandler: ApiErrorHandler

    private val adapter = HomeAdapter()

    companion object {
        fun toHome(context: Context) {
            context.run {
                startActivity(Intent(context, HomeActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        withBinding<ActivityHomeBinding> { binding ->
            binding.handle = this
            adapter.listener = {
                DetailActivity.toDetail(this, it)
            }
            adapter.addLoadStateListener {
                val isListEmpty = it.refresh is LoadState.NotLoading && adapter.itemCount == 0
                Timber.d(isListEmpty.toString())

                val isLoading = it.source.refresh is LoadState.Loading ||
                    it.source.append is LoadState.Loading ||
                    it.source.prepend is LoadState.Loading ||
                    it.refresh is LoadState.Loading ||
                    it.append is LoadState.Loading ||
                    it.prepend is LoadState.Loading

                viewModel.isLoadingSingleLive.postValue(isLoading)

                val errorState = it.source.refresh as? LoadState.Error
                    ?: it.source.append as? LoadState.Error
                    ?: it.source.prepend as? LoadState.Error
                    ?: it.refresh as? LoadState.Error
                    ?: it.append as? LoadState.Error
                    ?: it.prepend as? LoadState.Error

                errorState?.let { error ->
                    errorHandler.handleError(error.error)
                }
            }
            binding.recyclerView.adapter = adapter
            binding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
            binding.swipeRefresh.setOnRefreshListener {
                viewModel.callApi()
            }
            viewModel.listItem.observe(this) {
                binding.swipeRefresh.isRefreshing = false
                adapter.submitData(lifecycle, it)
            }
        }

        viewModel.isLoading.observe(this) {
            if (it) {
                progress.showLoadingProgress(this)
            } else {
                progress.hideLoadingProgress(this)
            }
        }

        viewModel.error.observe(this) {
            errorHandler.handleError(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.removeLoadStateListener { }
    }

    override fun onResume() {
        super.onResume()
        viewModel.callApi()
    }

    override fun didTapLogOut() {
        viewModel.cleanData()
        finish()
    }

    override fun didTapRefresh() {
        viewModel.callApi()
    }

    override fun didTapSort() {
        viewModel.sort()
    }

    override fun layoutId(): Int {
        return R.layout.activity_home
    }
}
