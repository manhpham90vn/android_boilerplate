package com.manhpham.baseandroid.ui.proxy

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.manhpham.baseandroid.R
import com.manhpham.baseandroid.ui.base.BaseActivity
import com.manhpham.baseandroid.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProxyActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.proxy_fragment_container) as NavHostFragment
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
