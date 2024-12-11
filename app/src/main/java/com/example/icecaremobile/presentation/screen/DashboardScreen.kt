package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.data.local.auth.AuthManagerImpl
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.DashboardUI


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(navController: NavHostController)
{
    val authManager = AuthManagerImpl(LocalContext.current)
    var loginResponse by remember { mutableStateOf<LoginResponse?>(null) }

    LaunchedEffect(Unit) {
        loginResponse = authManager.getLoginResponse()
    }

    Scaffold(
        Modifier.fillMaxSize()
    ){ padding ->

        var name by remember { mutableStateOf("") }
        var acctNumber by remember { mutableStateOf("") }
        var dollarRate by remember { mutableStateOf("") }
        var balance by remember { mutableStateOf("") }

        loginResponse?.let {
            name = it.data.fullName
            acctNumber = it.data.accountNumber
            dollarRate = it.data.dollarRate.toString()
            balance = it.data.accountBalance
        }

        DashboardUI(
            modifier = Modifier.padding(padding),
            name = name,
            acctNumber = acctNumber,
            dollarRate = dollarRate,
            balance = balance,
            onConverterClick = { navController.navigate(Screen.ConverterScreen) },
            onRemitStatusClick = {
                navController.navigate(Screen.RemitStatusScreen("isRemitStatus"))
            },
            onTransferStatusClick = {
                navController.navigate(Screen.RemitStatusScreen("isTransferStatus"))
            },
            onTransferMoneyClick = { navController.navigate(Screen.SendMoneyScreen) },
            onTopUpClick = { navController.navigate(Screen.SendMoneyScreen) },
            onViewHistoryClick = {  },
            notification = false
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview()
{
    val navController = rememberNavController()
    DashboardScreen(navController)
}