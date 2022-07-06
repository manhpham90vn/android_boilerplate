package com.example.baseandroid.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.baseandroid.R
import com.example.baseandroid.ui.base.BaseActivity
import com.example.baseandroid.ui.home.HomeActivity

class LoginActivity : BaseActivity() {

    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.button)
        viewModel = LoginViewModel(application)

        loginButton.setOnClickListener {
            log("start login")
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