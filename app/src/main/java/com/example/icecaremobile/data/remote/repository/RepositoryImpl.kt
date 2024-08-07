package com.example.icecaremobile.data.remote.repository

import com.example.icecaremobile.data.remote.implementation.ApiService
import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.network.ApiError
import com.example.icecaremobile.domain.repository.IRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: ApiService) : IRepository
{
        override suspend fun login(
        loginRequest: LoginRequest,
        onSuccess: (LoginResponse) -> Unit,
        onError: (ApiError) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.login(loginRequest)
                withContext(Dispatchers.Main) {
                    onSuccess(response)
                }
            } catch (e: Exception) {
                val apiError = ApiError(
                    message = e.message,
                    statusCode = 500,
                    responseMessage = "Failed to fetch weather data"
                )
                withContext(Dispatchers.Main) {
                    onError(apiError)
                }
            }
        }
    }
}