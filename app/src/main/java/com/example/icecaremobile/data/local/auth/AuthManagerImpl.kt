package com.example.icecaremobile.data.local.auth

import android.content.Context
import com.example.icecaremobile.core.utils.Constants
import com.example.icecaremobile.domain.auth.AuthManager
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthManagerImpl(context: Context) : AuthManager {

    private val sharedPreferences by lazy {
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }
    private val gson = Gson()
    private val userInfoKey = Constants.USER

    override suspend fun saveLoginResponse(loginResponse: LoginResponse) {
        withContext(Dispatchers.IO) {
            val userJson = gson.toJson(loginResponse)
            sharedPreferences.edit().putString(userInfoKey, userJson).apply()
        }
    }

    override suspend fun getLoginResponse(): LoginResponse? {
        return withContext(Dispatchers.IO) {
            val userJson = sharedPreferences.getString(userInfoKey, null)
            userJson?.let { gson.fromJson(it, LoginResponse::class.java) }
        }
    }

    override fun clearLoginResponse() {
        sharedPreferences.edit().remove(userInfoKey).apply()
    }
}

