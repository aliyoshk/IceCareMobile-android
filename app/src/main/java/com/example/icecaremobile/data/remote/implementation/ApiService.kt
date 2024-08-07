package com.example.icecaremobile.data.remote.implementation

import com.example.icecaremobile.data.remote.dataSource.IApiService
import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Response.LoginResponse
import javax.inject.Inject

class ApiService @Inject constructor(private val apiService: IApiService)
{
    suspend fun login(loginRequest: LoginRequest): LoginResponse
    {
        return apiService.login(loginRequest);
    }
}