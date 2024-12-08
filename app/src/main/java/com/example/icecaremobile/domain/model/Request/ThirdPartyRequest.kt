package com.example.icecaremobile.domain.model.Request

data class ThirdPartyRequest(
    val amount: Double,
    val bankName: String,
    val accountName: String,
    val accountNumber: String,
    val description: String,
    val customerEmail: String
)
