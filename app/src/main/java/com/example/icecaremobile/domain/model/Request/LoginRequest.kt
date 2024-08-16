package com.example.icecaremobile.domain.model.Request

data class LoginRequest(
    val username: String,
    val password: String,
    val device: Device
)

data class Device(
    val imei: String,
    val code: String,
    val deviceName: String,
    val isActiveDevice: Boolean,
    val os: String,
    val biometricToken: String,
    val biometricCode: String
)

