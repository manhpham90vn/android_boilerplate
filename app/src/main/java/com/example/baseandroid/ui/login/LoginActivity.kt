package com.example.baseandroid.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.baseandroid.R
import com.example.baseandroid.databinding.LoginBinding
import com.example.baseandroid.ui.base.BaseActivity
import com.example.baseandroid.ui.home.HomeActivity
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject lateinit var viewModel: LoginViewModel
    lateinit var binding: LoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.login)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.button.setOnClickListener {
            viewModel.login {
                if (it) {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Login error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (viewModel.isLogin()) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

}