package com.example.icecaremobile.domain.model.Response

import kotlinx.serialization.Serializable


@Serializable
data class TransactionHistoryResponse(
    val success: Boolean,
    val message: String,
    val data: List<TransactionHistory>
)

@Serializable
data class  TransactionHistory(
    val transactionDate: String,
    val totalAmount: String,
    val description: String,
    val category: String,
    val accountDetails: List<AccountDetails>
)

@Serializable
data class AccountDetails(
    val amount: String,
    val accountName: String,
    val accountNumber: String,
    val bankName: String
)