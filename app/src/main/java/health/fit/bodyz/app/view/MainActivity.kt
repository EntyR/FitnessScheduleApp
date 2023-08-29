package health.fit.bodyz.app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import health.fit.bodyz.app.R
import health.fit.bodyz.app.utils.DeviseBrandUtils
import health.fit.bodyz.app.utils.NetworkUtils
import health.fit.bodyz.app.utils.StorageUtils
import health.fit.bodyz.app.view.fragments.NoInternedExplanationFragment
import health.fit.bodyz.app.view.fragments.TimerAndScheduleFragment
import health.fit.bodyz.app.view.fragments.WebviewFragment
import health.fit.bodyz.app.view.viewmodels.WebViewViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), BackPressDelegation {

    private val viewModel by viewModels<WebViewViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
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


    override fun saveDelegate(onBackPressedCallback: OnBackPressedCallback) {
        onBackPressedDispatcher.addCallback (onBackPressedCallback)
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