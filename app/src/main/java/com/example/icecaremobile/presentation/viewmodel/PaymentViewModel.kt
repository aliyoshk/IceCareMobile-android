package com.example.icecaremobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icecaremobile.data.remote.entity.TransferResponseState
import com.example.icecaremobile.domain.model.Request.TransferRequest
import com.example.icecaremobile.domain.useCase.GetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val getTransferUseCase: GetUseCase
): ViewModel() {

    private val _transferResponse = MutableStateFlow<TransferResponseState>(TransferResponseState.Loading)
    val transferResponse: StateFlow<TransferResponseState> = _transferResponse

    fun fundTransfer(transferRequest: TransferRequest) {
        viewModelScope.launch {
            _transferResponse.value = TransferResponseState.Loading
            getTransferUseCase(
                transferRequest = transferRequest,
                onSuccess = { result ->
                    _transferResponse.value = TransferResponseState.Success(result)
                },
                onError = { error ->
                    _transferResponse.value = TransferResponseState.Error(error.concatenatedErrors)
                }
            )
        }
    }
    fun resetTransferState() {
        _transferResponse.value = TransferResponseState.Loading
    }
}