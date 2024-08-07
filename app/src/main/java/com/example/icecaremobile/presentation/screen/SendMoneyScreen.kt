package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.component.AppTopBar
import com.example.icecaremobile.presentation.ui.SendMoneyUI


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SendMoneyScreen(navController: NavHostController)
{
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(title = "Send Money", navigateBack = {})
        }
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
                navController.navigate(Screen.TransferScreen.route)
            else if (titleOption.contains("multiple", ignoreCase = true))
                navController.navigate(Screen.AccountScreen.route)
            else if (titleOption.contains("from", ignoreCase = true))
                navController.navigate(Screen.AccountBalanceTransferScreen.route)
            else if (titleOption.contains("third", ignoreCase = true))
                navController.navigate(Screen.ThirdPartyTransferScreen.route)
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