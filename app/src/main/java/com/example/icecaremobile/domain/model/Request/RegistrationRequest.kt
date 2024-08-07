package com.example.icecaremobile.domain.model.Request

data class RegistrationRequest(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
)


