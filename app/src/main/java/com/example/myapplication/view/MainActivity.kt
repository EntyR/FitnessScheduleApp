package com.example.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels

import com.example.myapplication.R
import com.example.myapplication.utils.DeviseBrandUtils
import com.example.myapplication.view.fragments.WebviewFragment
import com.example.myapplication.view.viewmodels.WebViewViewModel

class MainActivity : AppCompatActivity(), WebviewFragment.OnErrorCallback {

    private val viewModel by viewModels<WebViewViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (DeviseBrandUtils.checkIfGoogleBrand() || DeviseBrandUtils.checkIsEmu()) {
            loadDummy()
        }

        viewModel.mUrl.observe(this) {

            if (it.isBlank()) {
                Toast.makeText(
                    this,
                    "Internet error. Please checks internet connection and try again",
                    Toast.LENGTH_SHORT
                ).show()
                loadDummy()
            } else loadWebViewFragment(it)
        }
        viewModel.updateUrlValueIfNeeded(this@MainActivity)
    }

    private fun loadWebViewFragment(url: String) {
        val fragment = WebviewFragment.newInstance(url)
        supportFragmentManager.beginTransaction().replace(R.id.root, fragment).commit()
    }

    private fun loadDummy() = Unit

    override fun onError() {
        loadDummy()
    }
}