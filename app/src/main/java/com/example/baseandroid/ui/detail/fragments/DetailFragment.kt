package com.example.baseandroid.ui.detail.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import com.example.baseandroid.R
import com.example.baseandroid.databinding.FragmentDetailBinding
import com.example.baseandroid.di.ViewModelFactory
import com.example.baseandroid.ui.base.BaseFragment
import com.example.baseandroid.ui.detail.DetailHandle
import com.example.baseandroid.ui.detail.DetailViewModel
import com.wada811.databinding.withBinding
import javax.inject.Inject

class DetailFragment : BaseFragment(), DetailHandle {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<DetailViewModel>
    private val viewModel: DetailViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withBinding<FragmentDetailBinding> {
            it.viewModel = viewModel
            it.handle = this

            it.webview.settings.javaScriptEnabled = true
            it.webview.settings.domStorageEnabled = true
            it.webview.settings.cacheMode = WebSettings.LOAD_NO_CACHE
            it.webview.webChromeClient = WebChromeClient()
            it.webview.webViewClient = createWebViewClient()
            it.webview.loadUrl("https://zingnews.vn/")
        }
    }

    private fun createWebViewClient(): WebViewClient {
        return object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                withBinding<FragmentDetailBinding> {
                    it.buttonCloseWebview.text = "Start Loading"
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                withBinding<FragmentDetailBinding> {
                    it.buttonCloseWebview.text = "End Loading"
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        withBinding<FragmentDetailBinding> {
            it.buttonCloseWebview.text = "Destroy"
        }
    }

    override fun didTapClose() {
        requireActivity().finish()
    }

}