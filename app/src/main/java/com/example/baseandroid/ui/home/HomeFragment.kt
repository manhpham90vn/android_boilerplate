package com.example.baseandroid.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baseandroid.R
import com.example.baseandroid.databinding.FragmentHomeBinding
import com.example.baseandroid.extensions.setOnScrollEndVerticalListener
import com.example.baseandroid.models.PagingUserResponse
import com.example.baseandroid.service.ApiErrorHandler
import com.example.baseandroid.ui.base.BaseFragment
import com.example.baseandroid.ui.base.ScreenType
import com.wada811.databinding.withBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

interface HomeHandler {
    fun didTapLogOut()
    fun didTapRefresh()
    fun didTapSort()
}

@AndroidEntryPoint
class HomeFragment : BaseFragment(), HomeHandler {

    @Inject lateinit var errorHandler: ApiErrorHandler

    private val viewModel: HomeViewModel by viewModels()

    private val homeUserAdapter = HomeUserAdapter(mutableListOf())

    override fun initViewAndData() {
        withBinding<FragmentHomeBinding> { binding ->
            binding.handle = this
            binding.swipeRefresh.setOnRefreshListener {
                refreshCallApi()
            }
        }
    }

    override fun subscribeData() {
        viewModel.pagingResponse.observe(viewLifecycleOwner) {
            if (!it.array.isNullOrEmpty()) {
                initRcvUser(it.array)
            }
            withBinding<FragmentHomeBinding> { binding ->
                binding.swipeRefresh.isRefreshing = false
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

    private fun initRcvUser(listUser: List<PagingUserResponse>) {
        withBinding<FragmentHomeBinding> { binding ->
            if (binding.recyclerView.adapter == null) {
                binding.recyclerView.apply {
                    adapter = homeUserAdapter
                    layoutManager = LinearLayoutManager(this@HomeFragment.activity, RecyclerView.VERTICAL, false)
                    setOnScrollEndVerticalListener {
                        viewModel.checkExecuteGetListUser()
                    }
                }
            }
        }
        homeUserAdapter.addItemList(listUser)
    }

    private fun refreshCallApi() {
        homeUserAdapter.clear()
        viewModel.callApi()
    }

    override fun onResume() {
        super.onResume()
        refreshCallApi()
    }

    override fun didTapLogOut() {
        viewModel.cleanData()
        Navigation
            .findNavController(requireActivity(), R.id.proxy_fragment_container)
            .navigate(R.id.action_homeFragment_to_loginFragment)
    }

    override fun didTapRefresh() {
        refreshCallApi()
    }

    override fun didTapSort() {
        viewModel.sort()
        refreshCallApi()
    }

    override fun layoutId(): Int {
        return R.layout.fragment_home
    }

    override fun screenType(): ScreenType {
        return ScreenType.HOME
    }
}
