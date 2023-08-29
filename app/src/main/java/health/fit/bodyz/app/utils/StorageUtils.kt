package health.fit.bodyz.app.utils

import android.content.Context

object StorageUtils {

    fun getStoredUrl(ctx: Context): String? {
        return ctx.getSharedPreferences(ctx.packageName, Context.MODE_PRIVATE).getString(
            health.fit.bodyz.app.SHARED_CONFIG_URL_KEY, null)
    }

    fun setStoredUrl(ctx: Context, url: String) {
        val sharedPref = ctx.getSharedPreferences(ctx.packageName, Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString(health.fit.bodyz.app.SHARED_CONFIG_URL_KEY, url)
            apply()
        }
    }
}