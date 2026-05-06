package com.phathutshedzo.moneymanager.utils

import android.content.Context
import android.content.SharedPreferences

class AuthManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun register(username: String, password: String): Boolean {
        if (username.length < 3 || password.length < 4) {
            return false
        }

        // Clear old data for testing
        prefs.edit().clear().apply()

        prefs.edit()
            .putString("username", username)
            .putString("password", password)
            .putBoolean("isLoggedIn", true)
            .apply()
        return true
    }

    fun login(username: String, password: String): Boolean {
        val savedUsername = prefs.getString("username", "")
        val savedPassword = prefs.getString("password", "")

        if (username == savedUsername && password == savedPassword) {
            prefs.edit().putBoolean("isLoggedIn", true).apply()
            return true
        }
        return false
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean("isLoggedIn", false)

    fun logout() {
        prefs.edit().putBoolean("isLoggedIn", false).apply()
    }

    fun getUsername(): String = prefs.getString("username", "") ?: ""
}