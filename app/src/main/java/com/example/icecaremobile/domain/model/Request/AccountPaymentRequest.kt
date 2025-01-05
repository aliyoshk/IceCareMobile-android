package com.example.icecaremobile.domain.model.Request

data class AccountPaymentRequest(
    val amount: Double,
    val description: String,
    val customerEmail: String
)