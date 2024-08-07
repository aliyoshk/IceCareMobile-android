package com.example.icecaremobile.domain.useCase

import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.network.ApiError
import com.example.icecaremobile.domain.repository.IRepository
import javax.inject.Inject

class GetUseCase @Inject constructor(
    private val repository: IRepository
) {
    suspend operator fun invoke(
        loginRequest: LoginRequest,
        onSuccess: (LoginResponse) -> Unit,
        onError: (ApiError) -> Unit
    ) {
        repository.login(loginRequest, onSuccess, onError)
    }
}