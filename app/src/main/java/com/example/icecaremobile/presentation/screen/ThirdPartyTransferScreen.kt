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
import com.example.icecaremobile.presentation.ui.ThirdPartyTransferUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ThirdPartyTransferScreen(navHostController: NavHostController)
{
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { AppTopBar(title = "Transfer to third party", {}) }
    ) { padding ->

        ThirdPartyTransferUI(
            modifier = Modifier.padding(padding),
            amount = {},
            bankName = {},
            accountNumber = {},
            accountName = {},
            description = {},
            boxChecked = {},
            submitClicked= {}
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