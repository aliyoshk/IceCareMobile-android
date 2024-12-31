package com.example.icecaremobile.domain.model.Request

data class TopUpRequest(
    val currency: String,
    val description: String,
    val accountNo: String,
    val email: String,
    val phone: String,
    val bankDetails: List<BankDetail>,
    val transferEvidence: List<TransferEvidence>
)