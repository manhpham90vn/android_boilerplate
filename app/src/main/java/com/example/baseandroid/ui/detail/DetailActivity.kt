package com.example.baseandroid.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.baseandroid.R
import com.example.baseandroid.ui.base.BaseActivity
import com.example.baseandroid.ui.detail.fragments.DetailFragment

interface DetailHandle {
    fun didTapClose()
}

class DetailActivity : BaseActivity() {

    companion object {
        fun toDetail(context: Context) {
            context.run {
                startActivity(Intent(context, DetailActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.webviewContainer, DetailFragment())
                .commit()
        }
    }

    override fun layoutId(): Int {
        return R.layout.activity_detail
    }
}