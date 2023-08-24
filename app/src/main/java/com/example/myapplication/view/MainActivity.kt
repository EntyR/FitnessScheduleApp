package com.example.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope

import com.example.myapplication.R
import com.example.myapplication.utils.DeviseBrandUtils
import com.example.myapplication.utils.NetworkUtils
import com.example.myapplication.utils.StorageUtils
import com.example.myapplication.view.fragments.NoInternedExplanationFragment
import com.example.myapplication.view.fragments.TimerAndScheduleFragment
import com.example.myapplication.view.fragments.WebviewFragment
import com.example.myapplication.view.viewmodels.WebViewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.subscribe
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<WebViewViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadDummy()
        return
        val storedUrl = StorageUtils.getStoredUrl(this)
        if (!storedUrl.isNullOrEmpty()) {
            lifecycleScope.launch(Dispatchers.Default) {
                if (NetworkUtils.isInternetAvailable())
                    withContext(Dispatchers.Main) { loadWebViewFromRemote() }
                else withContext(Dispatchers.Main) { loadNoInternetExplanation() }

            }

        } else if (DeviseBrandUtils.checkIfGoogleBrand() || DeviseBrandUtils.checkIsEmu()) {
            loadDummy()
            return
        } else loadWebViewFromRemote()
    }

    private fun loadNoInternetExplanation() {
        val fragment = NoInternedExplanationFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.root, fragment).commit()
    }

    private fun loadWebViewFromRemote() {
        viewModel.updateUrlValueIfNeeded(this@MainActivity)
        viewModel.mUrl.observe(this) {
            if (it.isBlank()) {
                Toast.makeText(
                    this,
                    "Internet error. Please try again later",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                loadWebViewFragment(it)
            }
        }
    }

    private fun loadWebViewFragment(url: String) {
        val fragment = WebviewFragment.newInstance(url)
        supportFragmentManager.beginTransaction().replace(R.id.root, fragment).commit()
    }

    private fun loadDummy() {
        val fragment = TimerAndScheduleFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.root, fragment).commit()
    }
}