package com.example.baseandroid.ui.login.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.baseandroid.R
import com.example.baseandroid.databinding.FragmentLoginSuccessBinding
import com.example.baseandroid.di.ViewModelFactory
import com.example.baseandroid.ui.base.BaseFragment
import com.example.baseandroid.ui.home.HomeActivity
import com.example.baseandroid.ui.login.LoginViewModel
import com.wada811.databinding.withBinding
import javax.inject.Inject

class LoginSuccessFragment : BaseFragment(), LoginHandle {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<LoginViewModel>
    private val viewModel: LoginViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withBinding<FragmentLoginSuccessBinding> {
            it.viewModel = viewModel
            it.handle = this
        }
    }

    override fun didTapLogin() {
        HomeActivity.toHome(requireActivity())
    }

}