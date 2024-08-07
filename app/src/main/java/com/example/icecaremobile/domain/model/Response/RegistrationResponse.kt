package com.example.icecaremobile.domain.model.Response

data class RegistrationResponse(
    val status : Boolean,
    val message: String,
    val statusCode: Int,
    val data: String
)


