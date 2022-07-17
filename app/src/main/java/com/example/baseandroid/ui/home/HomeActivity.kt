package com.example.baseandroid.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.baseandroid.R
import com.example.baseandroid.databinding.ActivityHomeBinding
import com.example.baseandroid.di.ViewModelFactory
import com.example.baseandroid.ui.base.BaseActivity
import com.example.baseandroid.ui.detail.DetailActivity
import com.wada811.databinding.withBinding
import javax.inject.Inject

interface HomeHandler {
    fun didTapLogOut()
    fun didTapRefresh()
}

class HomeActivity : BaseActivity(), HomeHandler {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<HomeViewModel>
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

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
            val adapter = HomeAdapter()
            adapter.listener = {
                DetailActivity.toDetail(this, it)
            }
            binding.recyclerView.adapter = adapter
            binding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
            viewModel.listItem.observe(this) {
                adapter.set(it)
            }
        }

        viewModel.isLoading.observe(this) {
            if (it) {
                progress.showLoadingProgress(this)
            } else {
                progress.stopLoading()
            }
        }

        viewModel.error.observe(this) {
            viewModel.cleanData()
            finish()
        }
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

    override fun layoutId(): Int {
        return R.layout.activity_home
    }

}