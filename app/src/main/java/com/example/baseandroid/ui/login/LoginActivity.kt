package com.example.baseandroid.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.baseandroid.R
import com.example.baseandroid.ui.MyApplication
import com.example.baseandroid.ui.base.BaseActivity
import com.example.baseandroid.ui.home.HomeActivity
import timber.log.Timber
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.button)

        loginButton.setOnClickListener {
            Timber.d("start login")
            viewModel.login(email.text.toString(), password.text.toString()) {
                if (it) {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        if (viewModel.isLogin()) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

}