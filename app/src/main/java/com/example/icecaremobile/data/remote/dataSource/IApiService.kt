package com.example.icecaremobile.data.remote.dataSource

import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Request.RegistrationRequest
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.Response.RegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IApiService
{
    @POST("user/register")
    suspend fun registration(
        @Body registrationRequest: RegistrationRequest
    ): Response<RegistrationResponse>

    @POST("user/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}

