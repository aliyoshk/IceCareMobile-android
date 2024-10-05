package com.example.icecaremobile.data.provider.MockRepositoryProvider

import com.example.icecaremobile.data.provider.RepositoryProvider.IRepositoryProvider
import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Request.RegistrationRequest
import com.example.icecaremobile.domain.model.Response.CompanyAccounts
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.Response.RegistrationResponse
import com.example.icecaremobile.domain.model.Response.Response
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
                data = Response(
                    id = 6,
                    phone = "08034678912",
                    token = "rfgdssgdygsfguy463634",
                    status = "Approved",
                    accountNumber = "012567823",
                    accountBalance = "567000",
                    email = "johndoe@gmail.com",
                    dollarRate = 1500,
                    fullName = "John Doe",
                    companyNumber = "0905678934",
                    companyAccounts = listOf(
                        CompanyAccounts(
                            accountNumber = "2134789034",
                            accountName = "Ice Care Nig Ltd",
                            bankName = "UBA"
                        ),
                        CompanyAccounts(
                            accountNumber = "0252458264",
                            accountName = "Ice Care Nig Ltd",
                            bankName = "Wema Bank"
                        ),
                        CompanyAccounts(
                            accountNumber = "5678902314",
                            accountName = "Ice Care Nig Ltd",
                            bankName = "Providus Bank"
                        )
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
                        data = Response(
                            id = 6,
                            phone = "08034678912",
                            token = "rfgdssgdygsfguy463634",
                            status = "Approved",
                            accountNumber = "012567823",
                            accountBalance = "567000",
                            email = "johndoe@gmail.com",
                            dollarRate = 1500,
                            fullName = "John Doe",
                            companyNumber = "0905678934",
                            companyAccounts = listOf(
                                CompanyAccounts(
                                    accountNumber = "2134789034",
                                    accountName = "Ice Care Nig Ltd",
                                    bankName = "UBA"
                                ),
                                CompanyAccounts(
                                    accountNumber = "0252458264",
                                    accountName = "Ice Care Nig Ltd",
                                    bankName = "Wema Bank"
                                ),
                                CompanyAccounts(
                                    accountNumber = "5678902314",
                                    accountName = "Ice Care Nig Ltd",
                                    bankName = "Providus Bank"
                                )
                            )
                        )
                    )
                )
            }
        }
    }
}

