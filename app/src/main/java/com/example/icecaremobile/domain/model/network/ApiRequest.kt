package com.example.icecaremobile.domain.model.network

data class ApiRequest(
    val path: String,
    val payload: Any? = null,
    val headers: Map<String, String>? = null,
    val cookies: Map<String, String>? = null,
    val queryParams: Map<String, String>? = null
)
