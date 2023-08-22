package com.example.myapplication.utils

import android.content.Context
import com.example.myapplication.SHARED_CONFIG_URL_KEY

object StorageUtils {

    fun getStoredUrl(ctx: Context): String? {
        return ctx.getSharedPreferences(ctx.packageName, Context.MODE_PRIVATE).getString(
            SHARED_CONFIG_URL_KEY, null)
    }

    fun setStoredUrl(ctx: Context, url: String) {
        val sharedPref = ctx.getSharedPreferences(ctx.packageName, Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString(SHARED_CONFIG_URL_KEY, url)
            apply()
        }
    }
}