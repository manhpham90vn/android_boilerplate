package com.example.baseandroid.ui.home

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.baseandroid.R
import com.example.baseandroid.data.SharedPreferencesStorage
import com.example.baseandroid.networking.NetworkModule
import com.example.baseandroid.repository.AppLocalDataRepository
import com.example.baseandroid.repository.AppRemoteDataRepository
import com.example.baseandroid.ui.base.BaseActivity

class HomeActivity : BaseActivity() {

    lateinit var viewModel: HomeViewModel

    private val appLocalData by lazy {
        AppLocalDataRepository(SharedPreferencesStorage(applicationContext))
    }

    private val appRemoteData by lazy {
        AppRemoteDataRepository(NetworkModule(appLocalData).provideAppApi())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val logout = findViewById<Button>(R.id.logout)
        val refresh = findViewById<Button>(R.id.refresh)
        viewModel = HomeViewModel(appRemoteData, appLocalData)

        logout.setOnClickListener {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
            viewModel.cleanData()
            finish()
        }

        refresh.setOnClickListener {
            Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show()
            viewModel.callApi()
        }
    }

}