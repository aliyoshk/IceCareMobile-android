package com.example.icecaremobile.data.remote.dataSource

import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Request.RegistrationRequest
import com.example.icecaremobile.domain.model.Request.TransferRequest
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.Response.RegistrationResponse
import com.example.icecaremobile.domain.model.Response.TransferResponse
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

    @POST("user/fundTransfer")
    suspend fun transfer(@Body transferRequest: TransferRequest): Response<TransferResponse>
}

