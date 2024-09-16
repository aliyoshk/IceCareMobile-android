package com.example.icecaremobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icecaremobile.data.remote.entity.LoginResponseState
import com.example.icecaremobile.domain.model.Request.LoginRequest
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
    private val _loginResponse = MutableStateFlow<LoginResponseState>(LoginResponseState.Loading)
    val loginResponse: StateFlow<LoginResponseState> = _loginResponse


    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _loginResponse.value = LoginResponseState.Loading
            getWeatherUseCase(
                loginRequest = loginRequest,
                onSuccess = { result ->
                    _loginResponse.value = LoginResponseState.Success(result)
                },
                onError = { error ->
                    _loginResponse.value = LoginResponseState.Error(error.concatenatedErrors)
                }
            )
        }
    }
}

    fun clearError()
    {
        if (_loginResponse.value is LoginResponseState.Error) {
            _loginResponse.value = LoginResponseState.Loading
        }
    }
}

