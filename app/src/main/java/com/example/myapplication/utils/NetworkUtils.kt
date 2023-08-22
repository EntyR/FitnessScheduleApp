package com.example.myapplication.utils

import java.net.InetAddress
import java.net.UnknownHostException


object NetworkUtils {
    fun isInternetAvailable(): Boolean {
        val a = try {
            val address = InetAddress.getByName("www.google.com")
            !address.equals("")
        } catch (e: UnknownHostException) {
            false
        }
        return a
    }

}