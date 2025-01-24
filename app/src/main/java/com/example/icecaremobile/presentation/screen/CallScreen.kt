package com.example.icecaremobile.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.icecaremobile.data.local.auth.AuthManagerImpl
import com.example.icecaremobile.domain.model.Response.LoginResponseData
import com.example.icecaremobile.presentation.ui.CallUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar


@Composable
fun CallScreen(
    navController: NavController
) {
    val authManager = AuthManagerImpl(LocalContext.current)
    var userData by remember { mutableStateOf<LoginResponseData?>(null) }

    LaunchedEffect(Unit) {
        userData = authManager.getLoginResponse()?.data
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppTopBar(title = "Call Us") { navController.navigateUp() } }
    ) { padding ->

        CallUI(
            modifier = Modifier.padding(padding),
            numbers = userData?.userAccount?.companyNumber ?: emptyList()
        )
    }
}