package com.example.icecaremobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icecaremobile.data.remote.entity.LoginResponseState
import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.useCase.GetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getWeatherUseCase: GetUseCase
) : ViewModel()
{
    private val _state = MutableStateFlow<LoginResponseState>(LoginResponseState.Loading)
    val state: StateFlow<LoginResponseState> = _state


    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _state.value = LoginResponseState.Loading
            getWeatherUseCase(
                loginRequest= loginRequest,
                onSuccess = { result ->
                    _state.value = LoginResponseState.Success(result)
                },
                onError = { error ->
                    _state.value = LoginResponseState.Error(error.message ?: "Unknown error")
                }
            )
        }
    }

    fun clearError()
    {
        if (_state.value is LoginResponseState.Error) {
            _state.value = LoginResponseState.Loading
        }
    }
}