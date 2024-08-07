package com.example.icecaremobile.data.remote.repository

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
    private val apiService: ApiService
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
                    withContext(Dispatchers.Main) { onSuccess(response.body()!!) }
                } else {
                    withContext(Dispatchers.Main) { onError(ApiError("Login failed", response.code(), response.message())) }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) { onError(ApiError("Network error", 500, e.message)) }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) { onError(ApiError("Server error", e.code(), e.message)) }
            }


//            try {
//                val response = apiService.login(loginRequest)
//                withContext(Dispatchers.Main) {
//                    onSuccess(response)
//                }
//            } catch (e: Exception) {
//                val apiError = ApiError(
//                    message = e.message,
//                    statusCode = 500,
//                    responseMessage = "Failed to data"
//                )
//                withContext(Dispatchers.Main) {
//                    onError(apiError)
//                }
//            }
        }
    }
}

