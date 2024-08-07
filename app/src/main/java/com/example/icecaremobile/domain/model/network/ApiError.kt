package com.example.icecaremobile.domain.model.network

data class ApiError(
    val message: String? = null,
    val statusCode: Int? = null,
    val responseMessage: String? = null,
    val errors: List<String>? = null
) {
    val concatenatedErrors: String
        get() = errors?.joinToString("\n") ?: message ?: responseMessage.orEmpty()
}


