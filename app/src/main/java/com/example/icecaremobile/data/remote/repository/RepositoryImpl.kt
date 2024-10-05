package com.example.icecaremobile.data.remote.repository

import com.example.icecaremobile.data.local.dataSource.TokenManager
import com.example.icecaremobile.data.remote.implementation.ApiService
import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Request.RegistrationRequest
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.Response.RegistrationResponse
import com.example.icecaremobile.domain.model.network.ApiError
import com.example.icecaremobile.domain.repository.IRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : IRepository
{
    override suspend fun registration(
        registrationRequest: RegistrationRequest,
        onSuccess: (RegistrationResponse) -> Unit,
        onError: (ApiError) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val response = apiService.registration(registrationRequest)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) { onSuccess(response.body()!!) }
                } else {
                    withContext(Dispatchers.Main) { onError(ApiError("Login failed", response.code(), response.message())) }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) { onError(ApiError("Network error", 500, e.message)) }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) { onError(ApiError("Server error", e.code(), e.message)) }
            }
        }
    }

    override suspend fun login(
        loginRequest: LoginRequest,
        onSuccess: (LoginResponse) -> Unit,
        onError: (ApiError) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.login(loginRequest)
                if (response.isSuccessful) {
                    val loginResponse = response.body()!!
                    withContext(Dispatchers.Main) {
                        loginResponse.data.token.let { tokenManager.saveToken(it) }
                        onSuccess(loginResponse)
                    }
                } else {
                    withContext(Dispatchers.Main) { onError(ApiError("Login failed", response.code(), response.message())) }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) { onError(ApiError(e.message, 500, e.message)) }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) { onError(ApiError(e.message, e.code(), e.message)) }
            }
        }
    }
}

