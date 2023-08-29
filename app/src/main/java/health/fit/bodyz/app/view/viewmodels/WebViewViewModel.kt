package health.fit.bodyz.app.view.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import health.fit.bodyz.app.utils.FirebaseUtils
import health.fit.bodyz.app.utils.StorageUtils


class WebViewViewModel : ViewModel() {

    private val url: MutableLiveData<String> = MutableLiveData()
    val mUrl: LiveData<String> = url


    fun updateUrlValueIfNeeded(ctx: Context) {
        if (url.value != null)
            return
        FirebaseUtils.fetchRemoteConfig().addOnCompleteListener {
            val result = FirebaseUtils.getWebViewUrl()
            url.postValue(result)
            StorageUtils.setStoredUrl(ctx, result)
        }

    }
}