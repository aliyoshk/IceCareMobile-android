package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.TransferUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransferScreen(navController: NavHostController)
{
    Scaffold(
        topBar = { AppTopBar(title = "Transfer", {}) }
    ) { padding ->

        var bankList = listOf("Wema Bank", "GTBank", "First Bank")
        var selectedBankDetails = Triple("United Bank for Africa", "Ice Care Nig Ltd", "0123456789")
        var enteredDollarAmount by remember { mutableStateOf("") }
        var enteredNairaAmount by remember { mutableStateOf("") }
        var enteredPurpose by remember { mutableStateOf("") }
        var selectedBank by remember { mutableStateOf("") }
        var boxCheck by remember { mutableStateOf(false) }

        TransferUI(
            modifier = Modifier
                .padding(padding),
            banks = bankList,
            dollarAmount = { enteredDollarAmount = it},
            nairaAmount = { enteredNairaAmount = it },
            purpose = { enteredPurpose = it },
            selectedBank = { selectedBank = it },
            onChecked = { boxCheck = true},
            bankDetails = selectedBankDetails,
            onButtonClick =
            {
                navController.navigate(Screen.TransferSummaryScreen)
            }
        )
    }
}



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TransferScreenPreview()
{
    val navController = rememberNavController()
    TransferScreen(navController)
}