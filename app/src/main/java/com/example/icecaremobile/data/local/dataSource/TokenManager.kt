package com.example.icecaremobile.data.local.dataSource

import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val KEY_AUTH_TOKEN = "AUTH_TOKEN"
    }

    fun saveToken(token: String) {
        Log.d("TokenManager", "Saving token: $token")
        sharedPreferences.edit().putString(KEY_AUTH_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove(KEY_AUTH_TOKEN).apply()
    }
}