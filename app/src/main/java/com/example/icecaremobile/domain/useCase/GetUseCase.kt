package com.example.icecaremobile.domain.useCase

import com.example.icecaremobile.data.provider.RepositoryProvider.IRepositoryProvider
import com.example.icecaremobile.domain.auth.AuthManager
import com.example.icecaremobile.domain.model.Request.AccountPaymentRequest
import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Request.RegistrationRequest
import com.example.icecaremobile.domain.model.Request.ThirdPartyRequest
import com.example.icecaremobile.domain.model.Request.TransferRequest
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.Response.RegistrationResponse
import com.example.icecaremobile.domain.model.Response.TransferResponse
import com.example.icecaremobile.domain.model.network.ApiError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class GetUseCase @Inject constructor(
    private val repositoryProvider: IRepositoryProvider,  //IRepository
    private val authManager: AuthManager
) {
    val repository = repositoryProvider.provideRepository()

    //Registration block
    suspend operator fun invoke(
        registrationRequest: RegistrationRequest,
        onSuccess: (RegistrationResponse) -> Unit,
        onError: (ApiError) -> Unit
    ) {
        repository.registration(registrationRequest, onSuccess, onError)
    }

    //Login block
    suspend operator fun invoke(
        loginRequest: LoginRequest,
        onSuccess: (LoginResponse) -> Unit,
        onError: (ApiError) -> Unit
    ) {
        repository.login(loginRequest,
            onSuccess = { loginResponse ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        authManager.saveLoginResponse(loginResponse)
                        authManager.saveBankResponse(loginResponse.data.companyAccounts)
                        onSuccess(loginResponse)
                    } catch (e: Exception) {
                        onError(ApiError(message = e.message ?: "Error saving login response"))
                    }
                }
            },
            onError = onError
        )
    }

    // Transfer block
    suspend operator fun invoke(
        transferRequest: TransferRequest,
        onSuccess: (TransferResponse) -> Unit,
        onError: (ApiError) -> Unit
    ) {
        repository.transfer(transferRequest, onSuccess, onError)
    }

    //Account transfer loan
    suspend operator fun invoke(
        accountPaymentRequest : AccountPaymentRequest,
        onSuccess: (TransferResponse) -> Unit,
        onError: (ApiError) -> Unit
    ) {
        repository.accountTransfer(accountPaymentRequest, onSuccess, onError)
    }

    //Third party transfer loan
    suspend operator fun invoke(
        thirdPartyRequest: ThirdPartyRequest,
        onSuccess: (TransferResponse) -> Unit,
        onError: (ApiError) -> Unit
    ) {
        repository.thirdPartyTransfer(thirdPartyRequest, onSuccess, onError)
    }
}

//class GetUseCase @Inject constructor(
//    private val repository: IRepository
//) {
//    suspend operator fun invoke(
//        loginRequest: LoginRequest,
//        onSuccess: (LoginResponse) -> Unit,
//        onError: (ApiError) -> Unit
//    ) {
//        repository.login(loginRequest, onSuccess, onError)
//    }
//}
