package com.example.myapplication

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig

class FitnessApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.remoteConfig.fetchAndActivate().addOnFailureListener {
            it.printStackTrace()
        }
    }
}