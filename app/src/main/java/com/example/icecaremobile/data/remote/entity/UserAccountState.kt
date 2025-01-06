package com.example.icecaremobile.data.remote.entity

import com.example.icecaremobile.domain.model.Response.UserAccount

sealed class UserAccountState {
    data class Success(val userAccount: UserAccount) : UserAccountState()
    object Loading : UserAccountState()
    data class Error(val message: String) : UserAccountState()
}