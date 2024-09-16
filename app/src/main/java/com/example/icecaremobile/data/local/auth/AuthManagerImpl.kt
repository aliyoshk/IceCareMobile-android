package com.example.icecaremobile.data.local.auth

import android.content.Context
import com.example.icecaremobile.core.utils.Constants
import com.example.icecaremobile.domain.auth.AuthManager
import com.example.icecaremobile.domain.model.Response.CompanyAccounts
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
    private val bankListKey = Constants.BANKLIST

    override suspend fun saveLoginResponse(loginResponse: LoginResponse) {
        val userJson = gson.toJson(loginResponse)
        sharedPreferences.edit().putString(userInfoKey, userJson).apply()
    }

    override suspend fun getLoginResponse(): LoginResponse? {
        val userJson = sharedPreferences.getString(userInfoKey, null)
        return userJson?.let { gson.fromJson(it, LoginResponse::class.java) }
    }

    override suspend fun saveBankResponse(bankList: List<CompanyAccounts>) {
        val bankListJson = gson.toJson(bankList)
        sharedPreferences.edit().putString(bankListKey, bankListJson).apply()
    }

    override suspend fun getBankResponse(): List<CompanyAccounts>? {
        val bankListJson = sharedPreferences.getString(bankListKey, null)
        return bankListJson?.let {
            val type = object : TypeToken<List<CompanyAccounts>>() {}.type
            gson.fromJson(it, type)
        }
    }

    override fun logout() {
        sharedPreferences.edit().remove(userInfoKey).apply()
        sharedPreferences.edit().remove(bankListKey).apply()
    }
}


