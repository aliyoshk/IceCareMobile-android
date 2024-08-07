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
import com.example.icecaremobile.presentation.ui.ThirdPartyTransferUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ThirdPartyTransferScreen(navController: NavHostController)
{
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { AppTopBar(title = "Transfer to third party", {}) }
    ) { padding ->

        val message = " Your transfer details has been successful submitted\n" +
                "You’ll be notified once the transfer has been made."

        ThirdPartyTransferUI(
            modifier = Modifier.padding(padding),
            amount = {},
            bankName = {},
            accountNumber = {},
            accountName = {},
            description = {},
            boxChecked = {},
            submitClicked= {
                navController.navigate(Screen.SubmissionScreen.route + "/${message}/${Screen.ThirdPartyTransferScreen.route}")
            }
        )
    }
}



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ThirdPartyTransferScreenPreview()
{
    val navController = rememberNavController()
    ThirdPartyTransferScreen(navController)
}