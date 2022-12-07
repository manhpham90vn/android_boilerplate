package com.manhpham.baseandroid.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.manhpham.baseandroid.R
import com.manhpham.baseandroid.databinding.FragmentHomeBinding
import com.manhpham.baseandroid.service.ApiErrorHandler
import com.manhpham.baseandroid.ui.base.BaseFragment
import com.manhpham.baseandroid.ui.base.ScreenType
import com.wada811.databinding.withBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

interface HomeHandler {
    fun didTapLogOut()
    fun didTapRefresh()
    fun didTapSort()
}

@AndroidEntryPoint
class HomeFragment : BaseFragment(), HomeHandler {

    private val viewModel: HomeViewModel by viewModels()

    @Inject lateinit var errorHandler: ApiErrorHandler

    private val adapter = HomeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withBinding<FragmentHomeBinding> { binding ->
            binding.handle = this
            adapter.listener = { response ->
                response.type?.let {
                    when (it) {
                        "web" -> {
                            Navigation
                                .findNavController(requireActivity(), R.id.proxy_fragment_container)
                                .navigate(HomeFragmentDirections.actionHomeFragmentToDetailWebFragment(response.website!!))
                        }
                        "img" -> {
                            Navigation
                                .findNavController(requireActivity(), R.id.proxy_fragment_container)
                                .navigate(HomeFragmentDirections.actionHomeFragmentToDetailImageFragment(response.img!!))
                        }
                    }
                }
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
                    viewModel.singleLiveError.postValue(error.error)
                }
            }
            binding.recyclerView.adapter = adapter
            binding.recyclerView.addItemDecoration(DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL))
            binding.swipeRefresh.setOnRefreshListener {
                viewModel.callApi()
            }
            viewModel.listItem.observe(viewLifecycleOwner) {
                binding.swipeRefresh.isRefreshing = false
                adapter.submitData(lifecycle, it)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                progress.showLoadingProgress(requireActivity() as AppCompatActivity)
            } else {
                progress.hideLoadingProgress(requireActivity() as AppCompatActivity)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorHandler.handleError(it, screenType(), requireActivity() as AppCompatActivity)
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
        Navigation
            .findNavController(requireActivity(), R.id.proxy_fragment_container)
            .navigate(R.id.action_homeFragment_to_loginFragment)
    }

    override fun didTapRefresh() {
        viewModel.callApi()
    }

    override fun didTapSort() {
        viewModel.sort()
    }

    override fun layoutId(): Int {
        return R.layout.fragment_home
    }

    override fun screenType(): ScreenType {
        return ScreenType.HOME
    }
}
