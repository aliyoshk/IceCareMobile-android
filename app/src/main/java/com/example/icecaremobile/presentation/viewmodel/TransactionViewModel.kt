package com.example.icecaremobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icecaremobile.data.remote.entity.TransactionHistoryResponseState
import com.example.icecaremobile.data.remote.entity.TransferResponseState
import com.example.icecaremobile.domain.model.Request.StatusRequest
import com.example.icecaremobile.domain.useCase.GetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getUseCase: GetUseCase
): ViewModel() {

    private val _transferStatusResponse = MutableStateFlow<TransferResponseState>(TransferResponseState.Loading)
    val transferStatusResponse: StateFlow<TransferResponseState> = _transferStatusResponse

    private val _historyResponse = MutableStateFlow<TransactionHistoryResponseState>(TransactionHistoryResponseState.Loading)
    val historyResponse: StateFlow<TransactionHistoryResponseState> = _historyResponse


    fun getTransferStatus(statusRequest: StatusRequest) {
        viewModelScope.launch {
            _transferStatusResponse.value = TransferResponseState.Loading
            getUseCase(
                statusRequest = StatusRequest(statusRequest.email),
                onSuccess = { result ->
                    _transferStatusResponse.value = TransferResponseState.Success(result)
                },
                onError = { error ->
                    _transferStatusResponse.value = TransferResponseState.Error(error.concatenatedErrors)
                }
            )
        }
    }

    fun getTransactionHistory(email: String) {
        viewModelScope.launch {
            _historyResponse.value = TransactionHistoryResponseState.Loading
            getUseCase(
                email = email,
                onSuccess = { result ->
                    _historyResponse.value = TransactionHistoryResponseState.Success(result)
                },
                onError = { error ->
                    _historyResponse.value = TransactionHistoryResponseState.Error(error.concatenatedErrors)
                }
            )
        }
    }
}