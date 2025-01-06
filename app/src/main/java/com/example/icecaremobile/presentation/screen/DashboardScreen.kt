package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.navigation.NavHostController
import com.example.icecaremobile.data.local.auth.AuthManagerImpl
import com.example.icecaremobile.domain.model.Response.LoginResponseData
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.DashboardUI


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(navController: NavHostController)
{
    val authManager = AuthManagerImpl(LocalContext.current)
    var loginResponse by remember { mutableStateOf<LoginResponseData?>(null) }

    LaunchedEffect(Unit) {
        Log.e("Executing Dashboard launched effect", "Launched effect executed")
        loginResponse = authManager.getLoginResponse()?.data

        Log.e("Executing Dashboard launched effect", "Logging response $loginResponse")
    }

    Scaffold(
        Modifier.fillMaxSize()
    ){ padding ->

        Log.e("DashboardScreen", "Dashboard launched successful")

        if (loginResponse == null) {
            Log.e("DashboardScreen", "Login response is null")
            return@Scaffold
        }
        else {
            DashboardUI(
                modifier = Modifier.padding(padding),
                name = loginResponse?.fullName?.split(" ")?.firstOrNull() ?: "",
                acctNumber = loginResponse?.accountNumber ?: "",
                dollarRate = loginResponse?.userAccount?.dollarRate.toString(),
                balance = loginResponse?.userAccount?.nairaBalance ?: "",
                onConverterClick = { navController.navigate(Screen.ConverterScreen) },
                onRemitStatusClick = { navController.navigate(Screen.RemitStatusScreen("isRemitStatus")) },
                onTransferStatusClick = { navController.navigate(Screen.StatusScreen) },
                onTransferMoneyClick = { navController.navigate(Screen.ThirdPartyTransferScreen) },
                onTopUpClick = { navController.navigate(Screen.AccountScreen("isTopUpAccount")) },
                onViewHistoryClick = { navController.navigate(Screen.TransactionHistoryScreen) },
                notification = false
            )
        }
    }
}