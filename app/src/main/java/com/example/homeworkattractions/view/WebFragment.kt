package com.example.homeworkattractions.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homeworkattractions.databinding.FragmentWebviewBinding

class WebFragment : BaseFragment() {
    private val binding by lazy {
        FragmentWebviewBinding.inflate(LayoutInflater.from(requireContext()))
    }
    private val args: WebFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        initView()
    }

    private fun initView() {
        val title = args.title
        binding.viewToolbar.setTitle(title)
        val url = args.url
        binding.webView.setBackgroundColor(Color.WHITE)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        binding.webView.settings.domStorageEnabled = true
        binding.webView.settings.databaseEnabled = true
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(
                view: WebView?,
                url: String?,
                favicon: android.graphics.Bitmap?
            ) {
                super.onPageStarted(view, url, favicon)
                showLoadingDialog()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                dismissLoadingDialog()
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url ?: "")
                return true
            }
        }
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                Log.d("WebView", consoleMessage.message())
                return true
            }
        }
        binding.viewToolbar.backClickListener = {
            findNavController().popBackStack()
        }

        binding.webView.loadUrl(url)
    }
}