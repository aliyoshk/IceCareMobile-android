package com.example.icecaremobile.data.remote.dataSource

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
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IApiService
{
    @POST("user/register")
    suspend fun registration(@Body registrationRequest: RegistrationRequest): Response<RegistrationResponse>

    @POST("user/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("user/fundTransfer")
    suspend fun transfer(@Body transferRequest: TransferRequest): Response<TransferResponse>

    @POST("user/accountBalancePayment")
    suspend fun accountTransfer(@Body accountPaymentRequest: AccountPaymentRequest): Response<TransferResponse>

    @POST("user/accountTopUp")
    suspend fun accountTopUp(@Body topUpRequest: TopUpRequest): Response<TransferResponse>

    @POST("user/thirdPartyPayment")
    suspend fun thirdPartyTransfer(@Body thirdPartyRequest: ThirdPartyRequest): Response<TransferResponse>

    @GET("user/refreshAccount")
    suspend fun refreshAccount(@Query("email") email: String): Response<UserAccount>

    @GET("user/checkTransferStatus")
    suspend fun checkTransferStatus(@Query("email") email: String): Response<TransferResponse>

    @GET("user/getTransactionHistory")
    suspend fun getTransactionHistory(@Query("email") email: String): Response<TransactionHistoryResponse>

//    @GET("User/CheckRemitStatus/email={email}")
//    suspend fun checkRemitStatus(@Path("email") email: String): Response<TransferResponse>
}

