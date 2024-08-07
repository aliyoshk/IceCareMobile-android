package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.presentation.ui.MultipleTransferUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MultipleTransferScreen(navController: NavHostController)
{
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = { AppTopBar("Multiple Transfer") { } }
    ) { padding ->

        val bankList = listOf("Wema Bank", "GTBank", "First Bank")
        var selectedBanks by remember { mutableStateOf<List<String>>(emptyList()) }
        var bankAmounts by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
        var purposeOfPayment by remember { mutableStateOf("") }
        var dollarAmount by remember { mutableStateOf("") }
        var uploadedReceipts by remember { mutableStateOf<List<Uri>>(emptyList()) }

        MultipleTransferUI(
            modifier = Modifier.padding(padding),
            banks = bankList,
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
            onSubmitClick = {}
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