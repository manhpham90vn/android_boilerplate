package com.example.baseandroid.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.baseandroid.R
import com.example.baseandroid.di.ViewModelFactory
import com.example.baseandroid.models.PagingUserResponse
import com.example.baseandroid.ui.base.BaseActivity
import com.example.baseandroid.ui.detail.fragments.DetailFragment
import javax.inject.Inject

interface DetailHandle {
    fun didTapClose()
}

class DetailActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<DetailViewModel>
    private val viewModel: DetailViewModel by viewModels { viewModelFactory }

    companion object {
        private const val KEY_WEBSITE = "KEY_WEBSITE"

        fun toDetail(context: Context, item: PagingUserResponse) {
            context.run {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(KEY_WEBSITE, item.website)
                startActivity(intent)
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
        val data = intent.extras?.getString(KEY_WEBSITE)
        viewModel.url = data
    }

    override fun layoutId(): Int {
        return R.layout.activity_detail
    }
}