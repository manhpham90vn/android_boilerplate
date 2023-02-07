package com.manhpham.baseandroid.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.manhpham.baseandroid.R
import com.manhpham.baseandroid.databinding.FragmentLoginBinding
import com.manhpham.baseandroid.service.ApiErrorHandler
import com.manhpham.baseandroid.ui.base.BaseFragment
import com.manhpham.baseandroid.ui.base.ScreenType
import com.manhpham.baseandroid.ui.dialog.DialogManager
import com.manhpham.baseandroid.ui.dialog.TypeDialog
import com.manhpham.baseandroid.viewmodel.LoginViewModel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        withBinding<FragmentLoginBinding> {
            it.viewModel = viewModel
            it.lifecycleOwner = this
            it.handle = this
        }

        viewModel.loginResult.observe(
            viewLifecycleOwner,
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

        viewModel.throttledError.observe(viewLifecycleOwner) {
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
