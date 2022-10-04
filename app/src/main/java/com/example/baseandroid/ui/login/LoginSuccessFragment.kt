package com.example.baseandroid.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.baseandroid.R
import com.example.baseandroid.databinding.FragmentLoginSuccessBinding
import com.example.baseandroid.ui.base.BaseFragment
import com.example.baseandroid.ui.base.ScreenType
import com.wada811.databinding.withBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginSuccessFragment : BaseFragment(), LoginHandle {

    private val viewModel: LoginViewModel by activityViewModels()

    override fun initViewAndData() {
        withBinding<FragmentLoginSuccessBinding> {
            it.viewModel = viewModel
            it.handle = this
        }
    }

    override fun subscribeData() {

    }

    override fun didTapLogin() {
        Navigation
            .findNavController(requireActivity(), R.id.proxy_fragment_container)
            .navigate(R.id.action_loginSuccessFragment_to_homeFragment)
    }

    override fun layoutId(): Int {
        return R.layout.fragment_login_success
    }

    override fun screenType(): ScreenType {
        return ScreenType.LOGIN_SUCCESS
    }
}
