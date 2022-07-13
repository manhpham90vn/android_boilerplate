package com.example.baseandroid.ui.login.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.baseandroid.R
import com.example.baseandroid.databinding.FragmentLoginBinding
import com.example.baseandroid.di.ViewModelFactory
import com.example.baseandroid.ui.base.BaseFragment
import com.example.baseandroid.ui.login.LoginActivity
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        withBinding<FragmentLoginBinding> {
            it.viewModel = viewModel
            it.handle = this
        }
    }

    override fun didTapLogin() {
        viewModel.login {
            if (it) {
                LoginActivity.toLoginSuccess(requireActivity() as LoginActivity)
            } else {
                Toast.makeText(requireActivity(), "Login error", Toast.LENGTH_SHORT).show()
            }
        }
    }

}