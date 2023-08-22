package com.example.myapplication.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView

import com.example.myapplication.R



class WebviewFragment : Fragment() {

    interface OnErrorCallback {
        fun onError()
    }
    private lateinit var url: String

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = arguments?.getString(PARAM_URL, "")?: ""
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = requireView().findViewById(R.id.web_view)
        if (savedInstanceState != null) {
            webView.saveState(savedInstanceState)
            return
        }
        if (!this::url.isInitialized && url.isBlank()){
            (requireActivity() as OnErrorCallback).onError()
        } else webView.loadUrl(url)


        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        webView.settings.apply {
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            domStorageEnabled = true
            databaseEnabled = true
            setSupportZoom(false)
            allowFileAccess = true
            allowContentAccess = true
            loadWithOverviewMode = true
            useWideViewPort = true
        }

    }

    companion object {
        private const val PARAM_URL = "param_url"

        val layout = R.layout.fragment_webview
        @JvmStatic
        fun newInstance(url: String) =
            WebviewFragment().apply {
                arguments = Bundle().apply {
                    putString(PARAM_URL, url)
                }
            }
    }
}