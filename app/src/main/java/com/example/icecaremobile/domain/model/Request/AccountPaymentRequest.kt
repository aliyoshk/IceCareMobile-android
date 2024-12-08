package com.example.icecaremobile.domain.model.Request

data class AccountPaymentRequest(
    val nairaAmount: Double,
    val dollarAmount: Double,
    val description: String,
    val customerEmail: String
)