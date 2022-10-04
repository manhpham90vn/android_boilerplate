package com.example.baseandroid.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.baseandroid.R
import com.example.baseandroid.databinding.FragmentLoginBinding
import com.example.baseandroid.service.ApiErrorHandler
import com.example.baseandroid.ui.base.BaseFragment
import com.example.baseandroid.ui.base.ScreenType
import com.example.baseandroid.ui.dialog.DialogManager
import com.example.baseandroid.ui.dialog.TypeDialog
import com.wada811.databinding.withBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

interface LoginHandle {
    fun didTapLogin()
}

@AndroidEntryPoint
class LoginFragment : BaseFragment(), LoginHandle {

    private val viewModel: LoginViewModel by activityViewModels()

    @Inject lateinit var errorHandler: ApiErrorHandler
    @Inject lateinit var dialogManager: DialogManager

    override fun layoutId(): Int {
        return R.layout.fragment_login
    }

    override fun initViewAndData() {
        withBinding<FragmentLoginBinding> {
            it.viewModel = viewModel
            it.lifecycleOwner = this
            it.handle = this
        }
    }

    override fun subscribeData() {
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
                    dialogManager.showDialog(TypeDialog.RETRY_DIALOG, message = it.message)
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorHandler.handleError(it, screenType(), requireActivity() as AppCompatActivity) {
                viewModel.retryLogin()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                progress.showLoadingProgress(requireActivity() as AppCompatActivity)
            } else {
                progress.hideLoadingProgress(requireActivity() as AppCompatActivity)
            }
        }
    }

    override fun didTapLogin() {
        viewModel.login()
    }

    override fun screenType(): ScreenType {
        return ScreenType.LOGIN
    }
}
