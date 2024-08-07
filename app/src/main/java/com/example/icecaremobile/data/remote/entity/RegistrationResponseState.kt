package com.example.icecaremobile.data.remote.entity

import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.domain.model.Response.RegistrationResponse

sealed class RegistrationResponseState {
    data class Success(val registrationResponse: RegistrationResponse) : RegistrationResponseState()
    object Loading : RegistrationResponseState()
    data class Error(val message: String) : RegistrationResponseState()
}