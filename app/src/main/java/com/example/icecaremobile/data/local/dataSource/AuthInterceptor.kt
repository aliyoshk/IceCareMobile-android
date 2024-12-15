package com.example.icecaremobile.data.local.dataSource

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenManager.getToken()
        Log.e("AuthInterceptor", "Token used for Authorization: $token")
        Log.d("App Module Execution", "AuthInterceptor Token used for Authorization: $token")

        val requestBuilder = chain.request().newBuilder()
            .addHeader("accept", "*/*")
            .addHeader("Content-Type", "application/json")

        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        val request = requestBuilder.build()
        Log.e("AuthInterceptor", "Request Headers: ${request.headers}")
        return chain.proceed(request)
    }
}