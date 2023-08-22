package com.example.myapplication.view.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.utils.FirebaseUtils
import com.example.myapplication.utils.StorageUtils


class WebViewViewModel: ViewModel() {

    private val url: MutableLiveData<String> = MutableLiveData()
    val mUrl: LiveData<String> = url


    fun updateUrlValueIfNeeded(ctx: Context) {
        if (url.value != null)
            return
        val storedUrl = StorageUtils.getStoredUrl(ctx)
        if (storedUrl.isNullOrEmpty()) {
            FirebaseUtils.fetchRemoteConfig().addOnCompleteListener {
                url.postValue(FirebaseUtils.getWebViewUrl())
                StorageUtils.setStoredUrl(ctx, mUrl.value?:"") }
        }
    }
}