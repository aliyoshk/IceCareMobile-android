package com.example.icecaremobile.domain.useCase

import com.example.icecaremobile.data.provider.RepositoryProvider.IRepositoryProvider
import com.example.icecaremobile.domain.auth.AuthManager
import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Request.RegistrationRequest
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.Response.RegistrationResponse
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

    suspend operator fun invoke(
        registrationRequest: RegistrationRequest,
        onSuccess: (RegistrationResponse) -> Unit,
        onError: (ApiError) -> Unit
    ) {
        repository.registration(registrationRequest, onSuccess, onError)
    }


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
