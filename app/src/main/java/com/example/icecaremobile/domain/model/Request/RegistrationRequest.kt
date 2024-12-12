package com.example.icecaremobile.domain.model.Request

data class RegistrationRequest(
    val fullName: String,
    val email: String,
    val phone: String,
    val password: String,
)


