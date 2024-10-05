package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.data.local.auth.AuthManagerImpl
import com.example.icecaremobile.domain.model.Response.CompanyAccounts
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.MultipleTransferUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MultipleTransferScreen(navController: NavHostController)
{
    val message = "You transfer details has been successfully submitted for admin to verified. You will be notified once the transfer is confirmed.\n" +
            "\n" +
            "You can confirmed the status of your transfer in the dashboard\n"
    val destination = Screen.TransferSummaryScreen

    val authManager = AuthManagerImpl(LocalContext.current)
    var bankList by remember { mutableStateOf<List<CompanyAccounts>?>(null) }

    LaunchedEffect(Unit) {
        bankList = authManager.getBankResponse()
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = { AppTopBar("Multiple Transfer") { } }
    ) { padding ->

        var selectedBanks by remember { mutableStateOf<List<CompanyAccounts>>(emptyList()) }
        var bankAmounts by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
        var purposeOfPayment by remember { mutableStateOf("") }
        var dollarAmount by remember { mutableStateOf("") }
        var uploadedReceipts by remember { mutableStateOf<List<Uri>>(emptyList()) }

        MultipleTransferUI(
            modifier = Modifier.padding(padding),
            banks = bankList ?: emptyList(),
            dollarAmount = { dollarAmount = it },
            purpose = { purposeOfPayment = it },
            selectedBanks = { banks ->
                selectedBanks = banks
            },
            enteredAmounts = { amounts ->
                bankAmounts = amounts
            },
            receiptUploaded = { receipts ->
                uploadedReceipts = receipts
            },
            onSubmitClick = {
                navController.navigate(Screen.SubmissionScreen(key = destination.toString(), data = message))
            }
        )

        if (selectedBanks.isNotEmpty())
        {
            println("Dollar Amount: $dollarAmount")
            println("Purpose of Payment: $purposeOfPayment")

            bankAmounts.forEach { (bank, amount) ->
                println("Bank: $bank, Amount: $amount")
            }

            uploadedReceipts.forEach { uri ->
                println("File URI: $uri")
            }
        }
    }

}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MultipleTransferScreenPreview()
{
    val navController = rememberNavController()
    MultipleTransferScreen(navController)
}