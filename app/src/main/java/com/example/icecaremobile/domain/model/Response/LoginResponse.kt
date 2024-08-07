package com.example.icecaremobile.domain.model.Response

data class LoginResponse(
    val status : Boolean,
    val message: String,
    val statusCode: Int,
    val userProfile: UserProfile
)

data class UserProfile(
    val name: String,
    val accountBalance: Double,
    val email: String,
    val phoneNumber: String,
    val dollarRate: Double,
    val accountNumber: String,
    val isAccountVerified: Boolean,
    val banks : List<CompanyAccounts>
)

data class CompanyAccounts(
    val bankName: String,
    val accountName: String,
    val accountNumber: String
)

