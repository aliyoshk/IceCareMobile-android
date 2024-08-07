package com.example.icecaremobile.data.provider.MockRepositoryProvider

import com.example.icecaremobile.data.provider.RepositoryProvider.IRepositoryProvider
import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Response.Banks
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.Response.UserProfile
import com.example.icecaremobile.domain.model.network.ApiError
import com.example.icecaremobile.domain.repository.IRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockRepository @Inject constructor() : IRepositoryProvider
{
    companion object {
        fun provideMockResponse(): LoginResponse
        {
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
                    accountBalance = 723900.76,
                    banks = listOf(
                        Banks("Wema Bank", "01234567890", "Ice Care Nig Ltd"),
                        Banks("Union Bank", "01234567890", "Ice Care Nig Ltd"),
                        Banks("UBA", "01234567890", "Ice Care Nig Ltd"),
                        Banks("Taj Bank", "01234567890", "Ice Care Nig Ltd"),
                        Banks("Jaiz Bank", "01234567890", "Ice Care Nig Ltd")
                    )
                )
            )
            return mockLoginResponse
        }
    }

    override fun provideRepository(): IRepository
    {
        return object : IRepository {
            override suspend fun login(
                loginRequest: LoginRequest,
                onSuccess: (LoginResponse) -> Unit,
                onError: (ApiError) -> Unit
            ) {
                onSuccess(provideMockResponse())
            }
        }
    }
}

