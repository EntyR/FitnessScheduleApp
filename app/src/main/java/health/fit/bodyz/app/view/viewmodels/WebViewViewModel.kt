package health.fit.bodyz.app.view.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings


class WebViewViewModel : ViewModel() {

    lateinit var config: FirebaseRemoteConfig
    private val url: MutableLiveData<String> = MutableLiveData()
    val mUrl: LiveData<String> = url


    fun updateUrlValueIfNeeded(ctx: Context) {
        config = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)
            .build()
        config.setConfigSettingsAsync(configSettings)
        config.fetchAndActivate().addOnCompleteListener(OnCompleteListener<Boolean?> { task ->
            if (task.isSuccessful) {
                val find: String = config.getString("url")
                url.postValue(find)
            }
        })

    }
}