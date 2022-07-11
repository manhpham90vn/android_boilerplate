package com.example.baseandroid.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.baseandroid.R
import com.example.baseandroid.databinding.ActivityHomeBinding
import com.example.baseandroid.di.ViewModelFactory
import com.example.baseandroid.ui.base.BaseActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        withBinding<ActivityHomeBinding> {
            it.handle = this
        }
    }

    override fun didTapLogOut() {
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
        viewModel.cleanData()
        finish()
    }

    override fun didTapRefresh() {
        Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show()
        viewModel.callApi()
    }

    override fun layoutId(): Int {
        return R.layout.activity_home
    }

}