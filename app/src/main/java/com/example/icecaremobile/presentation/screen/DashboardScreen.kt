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
import com.example.icecaremobile.core.utils.Helpers
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
        var converterClick by remember { mutableStateOf(false) }
        var remitStatusClick by remember { mutableStateOf(false) }
        var transferStatusClick by remember { mutableStateOf(false) }
        var transferMoneyClick by remember { mutableStateOf(false) }
        var topUpMoneyClick by remember { mutableStateOf(false) }
        var viewHistoryClick by remember { mutableStateOf(false) }

        loginResponse?.let {
            name = it.data.fullName
            acctNumber = it.data.accountNumber
            dollarRate = it.data.dollarRate.toString()
            balance = it.data.accountBalance.toString()
        }

        DashboardUI(
            modifier = Modifier.padding(padding),
            name = name,
            acctNumber = acctNumber,
            dollarRate = Helpers.appendCurrency(dollarRate, "USD"),
            balance = Helpers.appendCurrency(balance),
            onConverterClick = { converterClick = true },
            onRemitStatusClick =
            {
                remitStatusClick = true
                navController.navigate(Screen.RemitStatusScreen("isRemitStatus"))
            },
            onTransferStatusClick = {
                navController.navigate(Screen.RemitStatusScreen("isTransferStatus"))
            },
            onTransferMoneyClick =
            {
                transferMoneyClick = true
                navController.navigate(Screen.SendMoneyScreen)
            },
            onTopUpClick = { topUpMoneyClick = true },
            onViewHistoryClick = { viewHistoryClick = true },
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