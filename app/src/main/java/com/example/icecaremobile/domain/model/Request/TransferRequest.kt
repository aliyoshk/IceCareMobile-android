package com.example.icecaremobile.domain.model.Request

data class TransferRequest(
    val dollarAmount: Double,
    val description: String,
    val bankDetails: List<BankDetail>,
    val transferEvidence: List<TransferEvidence>,
    val transactionDate: String,
    val customerEmail: String,
    val dollarRate: Double
)

data class BankDetail(
    val bankName: String,
    val transferredAmount: Double,
    val accountName: String,
    val accountNumber: String
)

data class TransferEvidence(
    val receipts: String
)
