package com.example.baseandroid.ui.home

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.baseandroid.R
import com.example.baseandroid.ui.MyApplication
import com.example.baseandroid.ui.base.BaseActivity

class HomeActivity : BaseActivity() {

    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val logout = findViewById<Button>(R.id.logout)
        val refresh = findViewById<Button>(R.id.refresh)
        viewModel = HomeViewModel(application)

        logout.setOnClickListener {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
            (application as MyApplication).appLocalData.cleanToken()
            (application as MyApplication).appLocalData.cleanRefreshToken()
            finish()
        }

        refresh.setOnClickListener {
            Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show()
            viewModel.callApi()
        }
    }

}