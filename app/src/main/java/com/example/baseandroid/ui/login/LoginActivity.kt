package com.example.baseandroid.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.baseandroid.R
import com.example.baseandroid.databinding.LoginBinding
import com.example.baseandroid.di.ViewModelFactory
import com.example.baseandroid.ui.base.BaseActivity
import com.example.baseandroid.ui.home.HomeActivity
import com.wada811.databinding.withBinding
import javax.inject.Inject

interface LoginHandle {
    fun didTapLogin()
}

class LoginActivity : BaseActivity(), LoginHandle {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<LoginViewModel>
    private val viewModel: LoginViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        withBinding<LoginBinding> {
            it.viewModel = viewModel
            it.handle = this
        }
        if (viewModel.isLogin()) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun didTapLogin() {
        viewModel.login {
            if (it) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Login error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun layoutId(): Int {
        return R.layout.login
    }

}