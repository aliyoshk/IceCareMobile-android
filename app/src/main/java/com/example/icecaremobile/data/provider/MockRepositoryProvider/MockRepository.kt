package com.example.icecaremobile.data.provider.MockRepositoryProvider

import com.example.icecaremobile.data.provider.RepositoryProvider.IRepositoryProvider
import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Request.RegistrationRequest
import com.example.icecaremobile.domain.model.Response.CompanyAccounts
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.Response.RegistrationResponse
import com.example.icecaremobile.domain.model.Response.UserProfile
import com.example.icecaremobile.domain.model.network.ApiError
import com.example.icecaremobile.domain.repository.IRepository
import kotlinx.coroutines.delay
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
        return object : IRepository
        {

            override suspend fun registration(
                registrationRequest: RegistrationRequest,
                onSuccess: (RegistrationResponse) -> Unit,
                onError: (ApiError) -> Unit
            ) {
                val message = "Thank you for registering with us! We have received your details and they are currently under review by our admin team. \n" +
                        "\n" +
                        "Please check back later to access your account and start using our services. We appreciate your patience and look forward to welcoming you on board!"
                delay(2000)
                onSuccess(
                    RegistrationResponse(
                        status = true,
                        statusCode = 200,
                        message = message,
                        data = message
                    )
                )
            }



            override suspend fun login(
                loginRequest: LoginRequest,
                onSuccess: (LoginResponse) -> Unit,
                onError: (ApiError) -> Unit
            ) {
                delay(2000)
                onSuccess(
                    LoginResponse(
                        message = "Login Successful",
                        statusCode = 200,
                        status = true,
                        userProfile = UserProfile(
                            dollarRate = 1670.25,
                            isAccountVerified = true,
                            name = "Isa Musa",
                            accountNumber = "012446645",
                            email = "isamusa@gmail.com",
                            phoneNumber = "080123456789",
                            accountBalance = 723900.76,
                            banks = listOf(
                                CompanyAccounts("Wema Bank", "01234567890", "Ice Care Nig Ltd"),
                                CompanyAccounts("Union Bank", "01234567890", "Ice Care Nig Ltd"),
                                CompanyAccounts("UBA", "01234567890", "Ice Care Nig Ltd"),
                                CompanyAccounts("Taj Bank", "01234567890", "Ice Care Nig Ltd"),
                                CompanyAccounts("Jaiz Bank", "01234567890", "Ice Care Nig Ltd"),
                                CompanyAccounts("Taj Bank", "01234567890", "Ice Care Nig Ltd"),
                                CompanyAccounts("Jaiz Bank", "01234567890", "Ice Care Nig Ltd")
                            )
                        )
                    )
                )
            }
        }
    }
}

