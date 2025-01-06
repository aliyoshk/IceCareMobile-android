package com.example.icecaremobile.domain.auth

import com.example.icecaremobile.domain.model.Response.CompanyAccounts
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.Response.UserAccount

interface AuthManager {
    suspend fun saveLoginResponse(loginResponse: LoginResponse)
    suspend fun getLoginResponse(): LoginResponse?
    suspend fun saveUserAccountResponse(userAccount: UserAccount)
    suspend fun getUserAccountResponse(): UserAccount?
    suspend fun saveBankResponse(bankList: List<CompanyAccounts>)
    suspend fun getBankResponse(): List<CompanyAccounts>?
    fun logout()
}


