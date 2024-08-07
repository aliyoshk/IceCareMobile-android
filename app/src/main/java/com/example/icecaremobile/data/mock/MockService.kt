package com.example.icecaremobile.data.mock

import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.Response.UserProfile
import com.example.icecaremobile.domain.model.network.ApiError
import com.example.icecaremobile.domain.repository.IRepository

class MockService : IRepository {
    override suspend fun login(
        loginRequest: LoginRequest,
        onSuccess: (LoginResponse) -> Unit,
        onError: (ApiError) -> Unit
    ){
        val mockLoginResponse = LoginResponse(
            message = "Login Successful",
            statusCode = 200,
            status = true,
            userProfile = UserProfile(
                dollarRate = 1500.2,
                isAccountVerified = true,
                name = "Isa Musa",
                accountNumber = "012446645",
                email = "isamusa@gmail.com",
                phoneNumber = "080123456789",
                accountBalance = 723900.76
            )
        )
        onSuccess(mockLoginResponse)
    }
}