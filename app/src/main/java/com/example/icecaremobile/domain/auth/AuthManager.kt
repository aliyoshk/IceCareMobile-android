package com.example.icecaremobile.domain.auth

import com.example.icecaremobile.domain.model.Response.LoginResponse

interface AuthManager {
    suspend fun saveLoginResponse(loginResponse: LoginResponse)
    suspend fun getLoginResponse(): LoginResponse?
    fun clearLoginResponse()
}
