package com.example.icecaremobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icecaremobile.data.remote.entity.RegistrationResponseState
import com.example.icecaremobile.domain.model.Request.RegistrationRequest
import com.example.icecaremobile.domain.useCase.GetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val getUseCase: GetUseCase
): ViewModel()
{
    private val _registrationResponse = MutableStateFlow<RegistrationResponseState>(RegistrationResponseState.Loading)
    val registrationResponse: StateFlow<RegistrationResponseState> = _registrationResponse

    fun registration(registrationRequest: RegistrationRequest) {
        viewModelScope.launch {
            _registrationResponse.value = RegistrationResponseState.Loading
            getUseCase(
                registrationRequest = registrationRequest,
                onSuccess = { result ->
                    _registrationResponse.value = RegistrationResponseState.Success(result)
                },
                onError = { error ->
                    _registrationResponse.value = RegistrationResponseState.Error(error.concatenatedErrors)
                }
            )
        }
    }
}