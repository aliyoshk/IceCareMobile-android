package com.example.icecaremobile.data.local.dataSource

import android.util.Log
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenManager.getToken()
        }
//        val request = chain.request().newBuilder()
//        request.addHeader("Authorization", "Bearer $token?")
//        return chain.proceed(request.build())

        val request = chain.request().newBuilder()
        request.addHeader("Accept", "application/json")
        request.addHeader("Content-Type", "application/json")
        request.addHeader("Authorization", "Bearer ${token?: ""}")

        val x = request.addHeader("Authorization", "Bearer ${token?: ""}")
        Log.d("Transfer response", "Token: $token")
        Log.d("Transfer response", x.toString())

        return chain.proceed(request.build())
    }
}