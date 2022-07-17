package com.example.baseandroid.ui.detail.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
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
    private val viewModel: DetailViewModel by activityViewModels { viewModelFactory }

    override fun layoutId(): Int {
        return R.layout.fragment_detail
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
            viewModel.url?.let { url ->
                it.webview.loadUrl(url)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                progress.showLoadingProgress(this)
            } else {
                progress.stopLoading()
            }
        }
    }

    private fun createWebViewClient(): WebViewClient {
        return object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                withBinding<FragmentDetailBinding> {
                    it.buttonCloseWebview.text = "Start Loading"
                    it.viewModel?.isLoading?.value = true
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                withBinding<FragmentDetailBinding> {
                    it.buttonCloseWebview.text = "End Loading"
                    it.viewModel?.isLoading?.value = false
                }
            }
        }
    }

    override fun didTapClose() {
        requireActivity().finish()
    }

}