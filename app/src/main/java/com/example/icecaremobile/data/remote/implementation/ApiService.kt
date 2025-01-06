package com.example.icecaremobile.data.remote.implementation

import com.example.icecaremobile.data.remote.dataSource.IApiService
import com.example.icecaremobile.domain.model.Request.AccountPaymentRequest
import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Request.RegistrationRequest
import com.example.icecaremobile.domain.model.Request.ThirdPartyRequest
import com.example.icecaremobile.domain.model.Request.TopUpRequest
import com.example.icecaremobile.domain.model.Request.TransferRequest
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.Response.RegistrationResponse
import com.example.icecaremobile.domain.model.Response.TransactionHistoryResponse
import com.example.icecaremobile.domain.model.Response.TransferResponse
import com.example.icecaremobile.domain.model.Response.UserAccount
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Inject

class ApiService @Inject constructor(private val apiService: IApiService) {

    suspend fun registration(@Body registrationRequest: RegistrationRequest): Response<RegistrationResponse> {
        return apiService.registration(registrationRequest)
    }

    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse> = apiService.login(loginRequest)

    suspend fun transfer(@Body transferRequest: TransferRequest): Response<TransferResponse> {
        return apiService.transfer(transferRequest)
    }

    suspend fun accountTransfer(@Body accountPaymentRequest: AccountPaymentRequest): Response<TransferResponse> {
        return apiService.accountTransfer(accountPaymentRequest)
    }

    suspend fun thirdPartyTransfer(@Body thirdPartyRequest: ThirdPartyRequest): Response<TransferResponse> {
        return apiService.thirdPartyTransfer(thirdPartyRequest)
    }

    suspend fun accountTopUp(@Body topUpRequest: TopUpRequest): Response<TransferResponse> {
        return apiService.accountTopUp(topUpRequest)
    }

    suspend fun refreshAccount(email: String): Response<UserAccount> {
        return apiService.refreshAccount(email)
    }

    suspend fun getTransferStatus(email: String): Response<TransferResponse> {
        return apiService.checkTransferStatus(email)
    }

    suspend fun getTransactionHistory(email: String): Response<TransactionHistoryResponse> {
        return apiService.getTransactionHistory(email)
    }
}

