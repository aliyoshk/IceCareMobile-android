package com.example.icecaremobile.data.remote.repository

import android.util.Log
import com.example.icecaremobile.data.local.dataSource.TokenManager
import com.example.icecaremobile.data.remote.implementation.ApiService
import com.example.icecaremobile.domain.model.Request.AccountPaymentRequest
import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Request.RegistrationRequest
import com.example.icecaremobile.domain.model.Request.ThirdPartyRequest
import com.example.icecaremobile.domain.model.Request.TransferRequest
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.Response.RegistrationResponse
import com.example.icecaremobile.domain.model.Response.TransferResponse
import com.example.icecaremobile.domain.model.network.ApiError
import com.example.icecaremobile.domain.repository.IRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : IRepository
{
    // Registration block
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
                    val errorBody = response.errorBody()?.string()
                    val json = try { JSONObject(errorBody?: "{}") } catch (e: Exception) { JSONObject() }
                    val message = json.optString("message")
                    val errorsJson = json.optJSONObject("errors")
                    val errorsList = mutableListOf<String>()
                    errorsJson?.keys()?.forEach { key ->
                        val errorArray = errorsJson.optJSONArray(key)
                        for (i in 0 until (errorArray?.length() ?: 0)) {
                            errorsList.add(errorArray?.getString(i) ?: "")
                        }
                    }
                    withContext(Dispatchers.Main) { onError(ApiError(message, response.code(), "Registration failed", errorsList)) }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) { onError(ApiError("Network error", 500, e.message)) }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) { onError(ApiError("Server error", e.code(), e.message)) }
            }
        }
    }

    //Login block
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
                    val errorBody = response.errorBody()?.string()
                    val json = try { JSONObject(errorBody?: "{}") } catch (e: Exception) { JSONObject() }
                    val message = json.optString("message")
                    val errorsJson = json.optJSONObject("errors")
                    val errorsList = mutableListOf<String>()
                    errorsJson?.keys()?.forEach { key ->
                        val errorArray = errorsJson.optJSONArray(key)
                        for (i in 0 until (errorArray?.length() ?: 0)) {
                            errorsList.add(errorArray?.getString(i) ?: "")
                        }
                    }

                    withContext(Dispatchers.Main) { onError(ApiError(message, response.code(), "Login failed", errorsList)) }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) { onError(ApiError(e.message, 500, e.message)) }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) { onError(ApiError(e.message, e.code(), e.message)) }
            }
        }
    }

    //Transfer block
    override suspend fun transfer(
        transferRequest: TransferRequest,
        onSuccess: (TransferResponse) -> Unit,
        onError: (ApiError) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val response = apiService.transfer(transferRequest)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) { onSuccess(response.body()!!) }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val json = try { JSONObject(errorBody?: "{}") } catch (e: Exception) { JSONObject() }
                    val message = json.optString("message")
                    val errorsJson = json.optJSONObject("errors")
                    val errorsList = mutableListOf<String>()
                    errorsJson?.keys()?.forEach { key ->
                        val errorArray = errorsJson.optJSONArray(key)
                        for (i in 0 until (errorArray?.length() ?: 0)) {
                            errorsList.add(errorArray?.getString(i) ?: "")
                        }
                    }
                    withContext(Dispatchers.Main) { onError(ApiError(message, response.code(), "${response.code()}\n${response.message()}", errorsList)) }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) { onError(ApiError(e.message, 500, "Network error")) }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) { onError(ApiError(e.message, e.code(), "Server error")) }
            }
        }
    }

    //Account transfer block
    override suspend fun accountTransfer(
        accountPaymentRequest: AccountPaymentRequest,
        onSuccess: (TransferResponse) -> Unit,
        onError: (ApiError) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val response = apiService.accountTransfer(accountPaymentRequest)
                Log.d("AccountTransfer", "Error body: $response")
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) { onSuccess(response.body()!!) }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val json = try { JSONObject(errorBody?: "{}") } catch (e: Exception) { JSONObject() }
                    val message = json.optString("message")
                    val errorsJson = json.optJSONObject("errors")
                    val errorsList = mutableListOf<String>()
                    errorsJson?.keys()?.forEach { key ->
                        val errorArray = errorsJson.optJSONArray(key)
                        for (i in 0 until (errorArray?.length() ?: 0)) {
                            errorsList.add(errorArray?.getString(i) ?: "")
                        }
                    }
                    Log.d("AccountTransfer", "Error body: $errorBody")
                    Log.e("AccountTransfer", "Error body: $message")
                    Log.e("AccountTransfer", "Error body: $errorsList")
                    withContext(Dispatchers.Main) { onError(ApiError(message, response.code(), "Transfer failed", errorsList)) }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) { onError(ApiError("Network error", 500, e.message)) }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) { onError(ApiError("Server error", e.code(), e.message)) }
            }
        }
    }

    //Third party transfer block
    override suspend fun thirdPartyTransfer(
        thirdPartyRequest: ThirdPartyRequest,
        onSuccess: (TransferResponse) -> Unit,
        onError: (ApiError) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val response = apiService.thirdPartyTransfer(thirdPartyRequest)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) { onSuccess(response.body()!!) }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val json = try { JSONObject(errorBody?: "{}") } catch (e: Exception) { JSONObject() }
                    val message = json.optString("message")
                    val errorsJson = json.optJSONObject("errors")
                    val errorsList = mutableListOf<String>()
                    errorsJson?.keys()?.forEach { key ->
                        val errorArray = errorsJson.optJSONArray(key)
                        for (i in 0 until (errorArray?.length() ?: 0)) {
                            errorsList.add(errorArray?.getString(i) ?: "")
                        }
                    }

                    withContext(Dispatchers.Main) { onError(ApiError(message, response.code(), "Transfer failed", errorsList)) }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) { onError(ApiError("Network error", 500, e.message)) }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) { onError(ApiError("Server error", e.code(), e.message)) }
            }
        }
    }
}

