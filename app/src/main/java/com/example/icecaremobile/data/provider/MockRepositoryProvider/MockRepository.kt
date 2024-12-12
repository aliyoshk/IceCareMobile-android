package com.example.icecaremobile.data.provider.MockRepositoryProvider

import com.example.icecaremobile.data.provider.RepositoryProvider.IRepositoryProvider
import com.example.icecaremobile.domain.model.Request.AccountPaymentRequest
import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Request.RegistrationRequest
import com.example.icecaremobile.domain.model.Request.ThirdPartyRequest
import com.example.icecaremobile.domain.model.Request.TransferRequest
import com.example.icecaremobile.domain.model.Response.CompanyAccounts
import com.example.icecaremobile.domain.model.Response.CompanyPhones
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.Response.RegistrationResponse
import com.example.icecaremobile.domain.model.Response.Response
import com.example.icecaremobile.domain.model.Response.TransferResponse
import com.example.icecaremobile.domain.model.network.ApiError
import com.example.icecaremobile.domain.repository.IRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockRepository @Inject constructor() : IRepositoryProvider
{
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
                            dollarRate = 1500.0,
                            fullName = "John Doe",
                            companyNumber = listOf(
                                CompanyPhones("0905678934"),
                                CompanyPhones("0803678900"),
                                CompanyPhones("0807678934")
                            ),
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

            // Mock Transfer response
            override suspend fun transfer(
                transferRequest: TransferRequest,
                onSuccess: (TransferResponse) -> Unit,
                onError: (ApiError) -> Unit
            ) {
                delay(2000)
                onSuccess(
                    TransferResponse(
                        status = true,
                        message = "You transfer details has been successfully submitted for admin to verified.",
                        data = true
                    )
                )
            }

            override suspend fun accountTransfer(
                accountPaymentRequest: AccountPaymentRequest,
                onSuccess: (TransferResponse) -> Unit,
                onError: (ApiError) -> Unit
            ) {
                delay(2000)
                onSuccess(
                    TransferResponse(
                        status = true,
                        message = "You request to debit from your account balance has been forwarded for admin to verified.",
                        data = true
                    )
                )
            }

            override suspend fun thirdPartyTransfer(
                thirdPartyRequest: ThirdPartyRequest,
                onSuccess: (TransferResponse) -> Unit,
                onError: (ApiError) -> Unit
            ) {
                delay(2000)
                onSuccess(
                    TransferResponse(
                        status = true,
                        message = "Your request to move your fund to third party account has been submitted for review",
                        data = true
                    )
                )
            }
        }
    }
}

