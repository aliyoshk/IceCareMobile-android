package com.example.icecaremobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icecaremobile.data.remote.entity.LoginResponseState
import com.example.icecaremobile.data.remote.entity.UserAccountState
import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Request.RefreshRequest
import com.example.icecaremobile.domain.model.Request.StatusRequest
import com.example.icecaremobile.domain.useCase.GetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUseCase: GetUseCase
) : ViewModel()
{
    private val _loginResponse = MutableStateFlow<LoginResponseState>(LoginResponseState.Loading)
    val loginResponse: StateFlow<LoginResponseState> = _loginResponse
    private val _userAccountResponse = MutableStateFlow<UserAccountState>(UserAccountState.Loading)
    val userAccountResponse: StateFlow<UserAccountState> = _userAccountResponse


    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _loginResponse.value = LoginResponseState.Loading
            getUseCase(
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

    fun refreshAccount(request: StatusRequest) {
        viewModelScope.launch {
            _userAccountResponse.value = UserAccountState.Loading
            getUseCase(
                request = RefreshRequest(request.email),
                onSuccess = { result ->
                    _userAccountResponse.value = UserAccountState.Success(result)
                },
                onError = { error ->
                    _userAccountResponse.value = UserAccountState.Error(error.concatenatedErrors)
                }
            )
        }
    }
}

