package com.example.icecaremobile.data.remote.entity

import com.example.icecaremobile.domain.model.Response.TransferResponse

sealed class TransferResponseState {
    data class Success(val transferResponse: TransferResponse) : TransferResponseState()
    object Loading : TransferResponseState()
    data class Error(val message: String) : TransferResponseState()
}