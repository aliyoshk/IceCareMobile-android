package com.example.icecaremobile.domain.repository

import com.example.icecaremobile.domain.model.Request.AccountPaymentRequest
import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Request.RegistrationRequest
import com.example.icecaremobile.domain.model.Request.ThirdPartyRequest
import com.example.icecaremobile.domain.model.Request.TransferRequest
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.Response.RegistrationResponse
import com.example.icecaremobile.domain.model.Response.TransferResponse
import com.example.icecaremobile.domain.model.network.ApiError

interface IRepository {

    suspend fun registration(
        registrationRequest: RegistrationRequest,
        onSuccess: (RegistrationResponse) -> Unit,
        onError: (ApiError) -> Unit
    )

    suspend fun login(
        loginRequest: LoginRequest,
        onSuccess: (LoginResponse) -> Unit,
        onError: (ApiError) -> Unit
    )

    suspend fun transfer(
        transferRequest: TransferRequest,
        onSuccess: (TransferResponse) -> Unit,
        onError: (ApiError) -> Unit
    )

    suspend fun accountTransfer(
        accountPaymentRequest : AccountPaymentRequest,
        onSuccess: (TransferResponse) -> Unit,
        onError: (ApiError) -> Unit
    )

    suspend fun thirdPartyTransfer(
        thirdPartyRequest: ThirdPartyRequest,
        onSuccess: (TransferResponse) -> Unit,
        onError: (ApiError) -> Unit
    )
}

