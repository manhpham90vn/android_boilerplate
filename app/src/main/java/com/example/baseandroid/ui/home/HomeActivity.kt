package com.example.baseandroid.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.baseandroid.R
import com.example.baseandroid.databinding.ActivityHomeBinding
import com.example.baseandroid.ui.base.BaseActivity
import javax.inject.Inject

class HomeActivity : BaseActivity() {

    @Inject lateinit var viewModel: HomeViewModel
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this
        binding.logout.setOnClickListener {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
            viewModel.cleanData()
            finish()
        }

        binding.refresh.setOnClickListener {
            Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show()
            viewModel.callApi()
        }
    }

}