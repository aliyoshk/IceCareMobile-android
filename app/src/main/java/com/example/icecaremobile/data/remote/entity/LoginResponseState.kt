package com.example.icecaremobile.data.remote.entity

import com.example.icecaremobile.domain.model.Response.LoginResponse


sealed class LoginResponseState {
    data class Success(val weatherData: LoginResponse) : LoginResponseState()
    object Loading : LoginResponseState()
    data class Error(val message: String) : LoginResponseState()
}