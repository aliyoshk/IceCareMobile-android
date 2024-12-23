package com.example.icecaremobile.presentation.navigator

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.icecaremobile.domain.model.Response.TransactionHistory
import com.example.icecaremobile.presentation.screen.AccountBalanceTransferScreen
import com.example.icecaremobile.presentation.screen.AccountScreen
import com.example.icecaremobile.presentation.screen.CallScreen
import com.example.icecaremobile.presentation.screen.ConverterScreen
import com.example.icecaremobile.presentation.screen.DashboardScreen
import com.example.icecaremobile.presentation.screen.LandingScreen
import com.example.icecaremobile.presentation.screen.LoginScreen
import com.example.icecaremobile.presentation.screen.MoreScreen
import com.example.icecaremobile.presentation.screen.MultipleTransferScreen
import com.example.icecaremobile.presentation.screen.ReceiptScreen
import com.example.icecaremobile.presentation.screen.RegistrationScreen
import com.example.icecaremobile.presentation.screen.RemitStatusScreen
import com.example.icecaremobile.presentation.screen.SendMoneyScreen
import com.example.icecaremobile.presentation.screen.StatusScreen
import com.example.icecaremobile.presentation.screen.SubmissionScreen
import com.example.icecaremobile.presentation.screen.ThirdPartyTransferScreen
import com.example.icecaremobile.presentation.screen.TransactionHistoryScreen
import com.example.icecaremobile.presentation.screen.TransferScreen
import com.example.icecaremobile.presentation.screen.TransferSummaryScreen
import com.example.icecaremobile.presentation.ui.component.AppBottomNavigationBar
import kotlin.reflect.typeOf

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Route() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            AppBottomNavigationBar(
                navController = navController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.LandingScreen,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Screen.LandingScreen> {
                LandingScreen(navController)
            }
            composable<Screen.LoginScreen> {
                LoginScreen(navController)
            }
            composable<Screen.RegistrationScreen> {
                RegistrationScreen(navController)
            }
            composable<Screen.DashboardScreen> {
                DashboardScreen(navController)
            }
            composable<Screen.SendMoneyScreen> {
                SendMoneyScreen(navController)
            }
            composable<Screen.NavSendMoneyScreen> {
                SendMoneyScreen(navController)
            }
            composable<Screen.AccountScreen> {
                AccountScreen(navController)
            }
            composable<Screen.TransferScreen> {
                TransferScreen(navController)
            }
            composable<Screen.TransferSummaryScreen> {
                val args = it.toRoute<Screen.TransferSummaryScreen>()
                TransferSummaryScreen(navController, args)
            }
            composable<Screen.MultipleTransferScreen> {
                MultipleTransferScreen(navController)
            }
            composable<Screen.AccountBalanceTransferScreen> {
                AccountBalanceTransferScreen(navController)
            }
            composable<Screen.ThirdPartyTransferScreen> {
                ThirdPartyTransferScreen(navController)
            }
            composable<Screen.RemitStatusScreen> {
                val args = it.toRoute<Screen.RemitStatusScreen>()
                RemitStatusScreen(navController, args.key)
            }
            composable<Screen.SubmissionScreen> {
                val args = it.toRoute<Screen.SubmissionScreen>()
                SubmissionScreen(navController, message = args.data, route = args.key)
            }
            composable<Screen.ConverterScreen> {
                ConverterScreen(navController)
            }
            composable<Screen.TransactionHistoryScreen> {
                TransactionHistoryScreen(navController)
            }
            composable<Screen.StatusScreen> {
                StatusScreen(navController)
            }
            composable<Screen.ReceiptScreen>(
                typeMap = mapOf(typeOf<TransactionHistory>() to CustomNavType.History)
            ) {
                val args = it.toRoute<Screen.ReceiptScreen>()
                ReceiptScreen(navController, transactionHistory = args.data)
            }
            composable<Screen.MoreScreen> {
                MoreScreen(navController)
            }
            composable<Screen.CallScreen> {
                CallScreen(navController)
            }
        }
    }
}