package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.TransferSummaryUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransferSummaryScreen(navController: NavHostController) {
    Scaffold(
        topBar = { AppTopBar(title = "Transfer Summary",  {}) }
    ) { padding ->

        val message = "You transfer details has been successfully submitted for admin to verified. You will be notified once the transfer is confirmed.\n" +
                "\n" +
                "You can confirmed the status of your transfer in the dashboard\n"
        val destination = Screen.TransferSummaryScreen

        TransferSummaryUI(
            modifier = Modifier.padding(padding),
            amountSent = "",
            dollarEquivalent = "",
            bankName = "",
            accountName = "",
            accountNumber = "",
            date = "",
            uploadedReceipt = { println("Selected file: $it") },
            onSubmitClick = {
                navController.navigate(
                    Screen.SubmissionScreen(
                        data = message,
                        key = destination.toString()
                    )
                )
            }
        )
    }
}



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TransferSummaryScreenPreview()
{
    val navController = rememberNavController()
    TransferSummaryScreen(navController)
}