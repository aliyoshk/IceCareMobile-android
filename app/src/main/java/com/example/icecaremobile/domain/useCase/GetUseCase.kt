package com.example.icecaremobile.domain.useCase

import com.example.icecaremobile.data.provider.RepositoryProvider.IRepositoryProvider
import com.example.icecaremobile.domain.auth.AuthManager
import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.network.ApiError
import com.example.icecaremobile.domain.repository.IRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

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

class GetUseCase @Inject constructor(
    private val repositoryProvider: IRepositoryProvider,
    private val authManager: AuthManager
) {
    suspend operator fun invoke(
        loginRequest: LoginRequest,
        onSuccess: (LoginResponse) -> Unit,
        onError: (ApiError) -> Unit
    ) {
        val repository = repositoryProvider.provideRepository()
        repository.login(loginRequest,
            onSuccess = { loginResponse ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        authManager.saveLoginResponse(loginResponse)
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
//    private val repository: IRepositoryProvider
//) {
//    suspend operator fun invoke(
//        loginRequest: LoginRequest,
//        onSuccess: (LoginResponse) -> Unit,
//        onError: (ApiError) -> Unit
//    ) {
//        val repository = repository.provideRepository()
//        repository.login(loginRequest, onSuccess, onError)
//    }
//}