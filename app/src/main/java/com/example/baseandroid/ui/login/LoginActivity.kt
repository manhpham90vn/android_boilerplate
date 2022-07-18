package com.example.baseandroid.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.baseandroid.R
import com.example.baseandroid.di.ViewModelFactory
import com.example.baseandroid.ui.base.BaseActivity
import com.example.baseandroid.ui.home.HomeActivity
import com.example.baseandroid.ui.login.fragments.LoginFragment
import com.example.baseandroid.ui.login.fragments.LoginSuccessFragment
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<LoginViewModel>
    private val viewModel: LoginViewModel by viewModels { viewModelFactory }

    // define navigation
    companion object {
        fun toLogin(context: Context) {
            context.run {
                startActivity(Intent(context, LoginActivity::class.java))
            }
        }

        fun toLoginSuccess(activity: AppCompatActivity) {
            activity
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, LoginSuccessFragment())
                .commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, LoginFragment())
                .commit()
        }

        if (viewModel.isLogin()) {
            HomeActivity.toHome(this)
        }
    }

    override fun layoutId(): Int {
        return R.layout.activity_login
    }
}
