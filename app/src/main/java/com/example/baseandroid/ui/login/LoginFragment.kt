package com.example.baseandroid.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.baseandroid.R
import com.example.baseandroid.databinding.FragmentLoginBinding
import com.example.baseandroid.di.ViewModelFactory
import com.example.baseandroid.networking.ApiErrorHandler
import com.example.baseandroid.ui.base.BaseFragment
import com.wada811.databinding.withBinding
import javax.inject.Inject

interface LoginHandle {
    fun didTapLogin()
}

class LoginFragment : BaseFragment(), LoginHandle {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<LoginViewModel>
    private val viewModel: LoginViewModel by activityViewModels { viewModelFactory }

    @Inject lateinit var errorHandler: ApiErrorHandler

    override fun layoutId(): Int {
        return R.layout.fragment_login
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        withBinding<FragmentLoginBinding> {
            it.viewModel = viewModel
            it.lifecycleOwner = this
            it.handle = this
        }

        viewModel.loginResult.observe(
            viewLifecycleOwner
        ) {
            when (it) {
                is LoginResult.LoginSuccess -> {
                    Toast.makeText(requireActivity(), "Login success", Toast.LENGTH_SHORT).show()
                    viewModel.cleanData()
                    Navigation
                        .findNavController(requireActivity(), R.id.proxy_fragment_container)
                        .navigate(R.id.action_loginFragment_to_loginSuccessFragment)
                }
                is LoginResult.LoginError -> {
                    Toast.makeText(
                        requireActivity(),
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorHandler.handleError(it, requireActivity() as AppCompatActivity)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                progress.showLoadingProgress(this)
            } else {
                progress.hideLoadingProgress(this)
            }
        }
    }

    override fun didTapLogin() {
        viewModel.login()
    }
}
