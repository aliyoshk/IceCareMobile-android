package com.example.icecaremobile.domain.repository

import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.network.ApiError
import javax.security.auth.login.LoginException

interface IRepository {
    suspend fun login(
        loginRequest: LoginRequest,
        onSuccess: (LoginResponse) -> Unit,
        onError: (ApiError) -> Unit
    )
}