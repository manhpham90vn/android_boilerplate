package com.example.baseandroid.ui.detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.baseandroid.R
import com.example.baseandroid.databinding.FragmentDetailWebBinding
import com.example.baseandroid.di.ViewModelFactory
import com.example.baseandroid.ui.base.BaseFragment
import com.wada811.databinding.withBinding
import javax.inject.Inject

interface DetailHandle {
    fun didTapClose()
}

class DetailWebFragment : BaseFragment(), DetailHandle {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<DetailViewModel>
    private val viewModel: DetailViewModel by viewModels { viewModelFactory }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withBinding<FragmentDetailWebBinding> {
            it.viewModel = viewModel
            it.handle = this

            it.webview.settings.javaScriptEnabled = true
            it.webview.settings.domStorageEnabled = true
            it.webview.settings.cacheMode = WebSettings.LOAD_NO_CACHE
            it.webview.webChromeClient = WebChromeClient()
            it.webview.webViewClient = createWebViewClient()
            arguments?.getString("key")?.let { url ->
                it.webview.loadUrl(url)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                progress.showLoadingProgress(requireActivity() as AppCompatActivity)
            } else {
                progress.hideLoadingProgress(requireActivity() as AppCompatActivity)
            }
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_detail_web
    }

    private fun createWebViewClient(): WebViewClient {
        return object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                withBinding<FragmentDetailWebBinding> {
                    it.buttonClose.text = "Start Loading"
                    it.viewModel?.isLoadingSingleLive?.postValue(true)
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                withBinding<FragmentDetailWebBinding> {
                    it.buttonClose.text = "End Loading"
                    it.viewModel?.isLoadingSingleLive?.postValue(false)
                }
            }
        }
    }

    override fun didTapClose() {
        activity?.onBackPressed()
    }
}
