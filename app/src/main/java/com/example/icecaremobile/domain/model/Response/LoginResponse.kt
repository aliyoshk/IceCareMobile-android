package com.example.icecaremobile.domain.model.Response

data class LoginResponse(
    val status : Boolean,
    val message: String,
    val statusCode: Int,
    val data: LoginResponseData
)

data class LoginResponseData(
    val id: Int,
    val fullName: String,
    val email: String,
    val token: String,
    val phone: String,
    val status: String,
    val accountNumber: String,
    val userAccount: UserAccount,
)

data class UserAccount(
    val dollarRate: Double,
    val nairaBalance: String,
    val dollarBalance: String,
    val companyNumber: List<CompanyPhones>,
    val companyAccounts: List<CompanyAccounts>
)

data class CompanyAccounts(
    val bankName: String,
    val accountName: String,
    val accountNumber: String
)

data class CompanyPhones(
    val phoneNumber: String
)
