package com.example.myapplication

import android.app.Application
import com.example.myapplication.utils.FirebaseUtils

class FitnessApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseUtils.setLatencyForUpdate(3000)
    }
}