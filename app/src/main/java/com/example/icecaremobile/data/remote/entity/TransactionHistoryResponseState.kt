package com.example.icecaremobile.data.remote.entity

import com.example.icecaremobile.domain.model.Response.TransactionHistoryResponse

sealed class TransactionHistoryResponseState {
    data class Success(val historyResponseState: TransactionHistoryResponse) : TransactionHistoryResponseState()
    object Loading : TransactionHistoryResponseState()
    data class Error(val message: String) : TransactionHistoryResponseState()
}