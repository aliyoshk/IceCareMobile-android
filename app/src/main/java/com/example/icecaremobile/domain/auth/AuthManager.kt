package com.example.icecaremobile.domain.auth

import com.example.icecaremobile.domain.model.Response.CompanyAccounts
import com.example.icecaremobile.domain.model.Response.LoginResponse

interface AuthManager {
    suspend fun saveLoginResponse(loginResponse: LoginResponse)
    suspend fun getLoginResponse(): LoginResponse?
    suspend fun saveBankResponse(bankList: List<CompanyAccounts>)
    suspend fun getBankResponse(): List<CompanyAccounts>?
    fun logout()
}


