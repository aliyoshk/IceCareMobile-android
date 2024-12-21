package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.SendMoneyUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SendMoneyScreen(navController: NavHostController)
{
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppTopBar(title = "Send Money"){ navController.navigateUp() }}
    ) { padding ->

        val context = LocalContext.current
        var titleOption by remember { mutableStateOf("") }
        var onClicked by remember { mutableStateOf(false) }

        SendMoneyUI(
            modifier = Modifier.padding(padding),
            optionTitle = { titleOption = it },
            onClick = { onClicked = true },
        )

        if (onClicked)
        {
            onClicked = false
            if (titleOption.contains("single", ignoreCase = true))
                navController.navigate(Screen.TransferScreen)
            else if (titleOption.contains("multiple", ignoreCase = true))
                navController.navigate(Screen.AccountScreen)
            else if (titleOption.contains("from", ignoreCase = true))
                navController.navigate(Screen.AccountBalanceTransferScreen)
            else if (titleOption.contains("third", ignoreCase = true))
                navController.navigate(Screen.ThirdPartyTransferScreen)
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SendMoneyScreenPreview()
{
    val navController = rememberNavController()
    SendMoneyScreen(navController)
}