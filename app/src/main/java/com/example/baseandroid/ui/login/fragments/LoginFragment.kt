package com.example.baseandroid.ui.login.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.baseandroid.R
import com.example.baseandroid.databinding.FragmentLoginBinding
import com.example.baseandroid.di.ViewModelFactory
import com.example.baseandroid.ui.base.BaseFragment
import com.example.baseandroid.ui.login.LoginActivity
import com.example.baseandroid.ui.login.LoginResult
import com.example.baseandroid.ui.login.LoginViewModel
import com.wada811.databinding.withBinding
import javax.inject.Inject

interface LoginHandle {
    fun didTapLogin()
}

class LoginFragment : BaseFragment(), LoginHandle {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<LoginViewModel>
    private val viewModel: LoginViewModel by activityViewModels { viewModelFactory }

    override fun layoutId(): Int {
        return R.layout.fragment_login
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        withBinding<FragmentLoginBinding> {
            it.viewModel = viewModel
            it.handle = this
        }

        viewModel.loginResult.observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    is LoginResult.LoginSuccess -> {
                        Toast.makeText(requireActivity(), "Login success", Toast.LENGTH_SHORT).show()
                        LoginActivity.toLoginSuccess(requireActivity() as AppCompatActivity)
                    }
                    is LoginResult.LoginError -> {
                        Toast.makeText(requireActivity(), "Login error: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                progress.showLoadingProgress(this)
            } else {
                progress.stopLoading()
            }
        }
    }

    override fun didTapLogin() {
        viewModel.login()
    }
}
