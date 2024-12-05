package com.example.icecaremobile.presentation.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.icecaremobile.presentation.screen.AccountBalanceTransferScreen
import com.example.icecaremobile.presentation.screen.AccountScreen
import com.example.icecaremobile.presentation.screen.DashboardScreen
import com.example.icecaremobile.presentation.screen.LandingScreen
import com.example.icecaremobile.presentation.screen.LoginScreen
import com.example.icecaremobile.presentation.screen.MultipleTransferScreen
import com.example.icecaremobile.presentation.screen.RegistrationScreen
import com.example.icecaremobile.presentation.screen.RemitStatusScreen
import com.example.icecaremobile.presentation.screen.SendMoneyScreen
import com.example.icecaremobile.presentation.screen.SubmissionScreen
import com.example.icecaremobile.presentation.screen.ThirdPartyTransferScreen
import com.example.icecaremobile.presentation.screen.TransferScreen
import com.example.icecaremobile.presentation.screen.TransferSummaryScreen

@Composable
fun Route()
{
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.LandingScreen
    ) {
        composable<Screen.LandingScreen>
        {
            LandingScreen(navController)
        }
        composable<Screen.LoginScreen>
        {
            LoginScreen(navController)
        }
        composable<Screen.RegistrationScreen>
        {
            RegistrationScreen(navController)
        }
        composable<Screen.DashboardScreen>
        {
            DashboardScreen(navController)
        }
        composable<Screen.SendMoneyScreen>
        {
            SendMoneyScreen(navController)
        }
        composable<Screen.AccountScreen>
        {
            AccountScreen(navController)
        }
        composable<Screen.TransferScreen>
        {
            TransferScreen(navController)
        }
        composable<Screen.TransferSummaryScreen>
        {
            val args = it.toRoute<Screen.TransferSummaryScreen>()
            TransferSummaryScreen(navController, args)
        }
        composable<Screen.MultipleTransferScreen>
        {
            MultipleTransferScreen(navController)
        }
        composable<Screen.AccountBalanceTransferScreen>
        {
            AccountBalanceTransferScreen(navController)
        }
        composable<Screen.ThirdPartyTransferScreen>
        {
            ThirdPartyTransferScreen(navController)
        }
        composable<Screen.RemitStatusScreen>
        {
            val args = it.toRoute<Screen.RemitStatusScreen>()
            RemitStatusScreen(navController, args.key)
        }
        composable<Screen.SubmissionScreen>
        {
            val args = it.toRoute<Screen.SubmissionScreen>()
            SubmissionScreen(navController, message = args.data, route = args.key)
        }
    }
}