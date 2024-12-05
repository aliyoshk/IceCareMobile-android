package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.AccountBalanceTransferUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccountBalanceTransferScreen(navController: NavHostController)
{
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { AppTopBar(title = "Pay from account balance")}
    ) { padding ->

        val message = "Your request has been successfully submitted for admin to verified. You will be notified once confirmed.\n" +
                "\n" +
                "You can confirmed the status of your transfer in the dashboard\n"

        AccountBalanceTransferUI(
            modifier = Modifier.padding(padding),
            nairaAmountEntered = {},
            dollarEquivalence = "",
            description = {},
            boxCheck = {},
            buttonClicked = {
                navController.navigate(Screen.SubmissionScreen(
                    key = Screen.AccountBalanceTransferScreen.toString(),
                    data = message
                ))
            }
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountBalanceTransferScreenPreview(){
    val navController = rememberNavController()
    AccountBalanceTransferScreen(navController)
}