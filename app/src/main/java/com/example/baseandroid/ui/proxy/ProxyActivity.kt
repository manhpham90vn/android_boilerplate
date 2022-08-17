package com.example.baseandroid.ui.proxy

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.baseandroid.R
import com.example.baseandroid.di.ViewModelFactory
import com.example.baseandroid.ui.base.BaseActivity
import com.example.baseandroid.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_proxy.*
import javax.inject.Inject

class ProxyActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<LoginViewModel>
    private val viewModel: LoginViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = proxy_fragment_container as NavHostFragment
        val navController = navHostFragment.navController
        val graph = navController.navInflater.inflate(R.navigation.app_nav)

        if (viewModel.isLogin()) {
            graph.setStartDestination(R.id.homeFragment)
        } else {
            graph.setStartDestination(R.id.loginFragment)
        }

        navController.setGraph(graph = graph, startDestinationArgs = null)
    }

    override fun layoutId(): Int {
        return R.layout.activity_proxy
    }
}