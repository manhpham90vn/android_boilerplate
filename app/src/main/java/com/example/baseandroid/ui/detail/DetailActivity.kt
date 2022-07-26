package com.example.baseandroid.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.baseandroid.R
import com.example.baseandroid.di.ViewModelFactory
import com.example.baseandroid.models.PagingUserResponse
import com.example.baseandroid.ui.base.BaseActivity
import com.example.baseandroid.ui.detail.fragments.DetailImageFragment
import com.example.baseandroid.ui.detail.fragments.DetailWebFragment
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
        private const val KEY_IMAGE_URL = "KEY_IMAGE_URL"

        fun toDetail(context: Context, item: PagingUserResponse) {
            context.run {
                item.type?.let {
                    when (it) {
                        "web" -> {
                            val intent = Intent(context, DetailActivity::class.java)
                            intent.putExtra(KEY_WEBSITE, item.website)
                            startActivity(intent)
                        }
                        "img" -> {
                            val intent = Intent(context, DetailActivity::class.java)
                            intent.putExtra(KEY_IMAGE_URL, item.img)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            intent.extras?.getString(KEY_WEBSITE)?.let {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, DetailWebFragment())
                    .commit()
                viewModel.url = it
            }
            intent.extras?.getString(KEY_IMAGE_URL)?.let {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, DetailImageFragment())
                    .commit()
                viewModel.img = it
            }
        }
    }

    override fun layoutId(): Int {
        return R.layout.activity_detail
    }
}
