package com.example.icecaremobile.data.remote.implementation

import com.example.icecaremobile.data.remote.dataSource.IApiService
import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Request.RegistrationRequest
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.Response.RegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Inject

class ApiService @Inject constructor(private val apiService: IApiService)
{
    suspend fun registration(@Body registrationRequest: RegistrationRequest): Response<RegistrationResponse>
    {
        return apiService.registration(registrationRequest);
    }

    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
    {
        return apiService.login(loginRequest);
    }
}

