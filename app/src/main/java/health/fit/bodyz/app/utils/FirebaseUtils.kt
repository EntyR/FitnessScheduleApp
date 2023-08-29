package health.fit.bodyz.app.utils

import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

object FirebaseUtils {

     fun fetchRemoteConfig(): Task<Boolean> {
          return Firebase.remoteConfig.fetchAndActivate()
               .addOnFailureListener {
                    it.printStackTrace()

          }
     }

     fun setLatencyForUpdate(latencyMili: Int) {
          val configSetting = remoteConfigSettings {
               minimumFetchIntervalInSeconds = latencyMili.toLong()
          }
          Firebase.remoteConfig.setConfigSettingsAsync(configSetting)
     }

     fun getWebViewUrl(): String {
          return Firebase.remoteConfig.getString(health.fit.bodyz.app.KEY_WEB_VIEW_URL)
     }

}