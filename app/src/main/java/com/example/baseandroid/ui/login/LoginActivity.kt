package com.example.baseandroid.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.baseandroid.R
import com.example.baseandroid.data.SharedPreferencesStorage
import com.example.baseandroid.networking.NetworkModule
import com.example.baseandroid.repository.AppLocalDataRepository
import com.example.baseandroid.repository.AppRemoteDataRepository
import com.example.baseandroid.ui.base.BaseActivity
import com.example.baseandroid.ui.home.HomeActivity

class LoginActivity : BaseActivity() {

    lateinit var viewModel: LoginViewModel

    private val appLocalData by lazy {
        AppLocalDataRepository(SharedPreferencesStorage(this))
    }

    private val appRemoteData by lazy {
        AppRemoteDataRepository(NetworkModule(appLocalData).provideAppApi())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.button)
        viewModel = LoginViewModel(appLocalData, appRemoteData)

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