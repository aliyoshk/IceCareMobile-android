package com.example.icecaremobile.presentation.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
        startDestination = Screen.LandingScreen.route
    ) {
        composable(route = Screen.LandingScreen.route)
        {
            LandingScreen(navController)
        }
        composable(route = Screen.LoginScreen.route)
        {
            LoginScreen(navController)
        }
        composable(Screen.RegistrationScreen.route)
        {
            RegistrationScreen(navController)
        }
        composable(Screen.DashboardScreen.route)
        {
            DashboardScreen(navController)
        }
        composable(Screen.SendMoneyScreen.route)
        {
            SendMoneyScreen(navController)
        }
        composable(Screen.AccountScreen.route)
        {
            AccountScreen(navController)
        }
        composable(Screen.TransferScreen.route)
        {
            TransferScreen(navController)
        }
        composable(Screen.TransferSummaryScreen.route)
        {
            TransferSummaryScreen(navController)
        }
        composable(Screen.MultipleTransferScreen.route)
        {
            MultipleTransferScreen(navController)
        }
        composable(Screen.AccountBalanceTransferScreen.route)
        {
            AccountBalanceTransferScreen(navController)
        }
        composable(Screen.ThirdPartyTransferScreen.route)
        {
            ThirdPartyTransferScreen(navController)
        }
        composable(Screen.RemitStatusScreen.route)
        {
            RemitStatusScreen(navController)
        }
        composable(Screen.SubmissionScreen.route)
        {
            SubmissionScreen(navController)
        }

//        composable(
//            Screen.DetailsScreen.route + "/{bundle}",
//            arguments = listOf(
//                navArgument("data")
//                {
//                    type = NavType.StringType
//                    defaultValue = "Empty value return"
//                    nullable = true
//                }
//            )
//        )
//        {
//            val data = it.arguments?.getString("bundle")!!
//            Detail(data = data)
//        }
    }
}