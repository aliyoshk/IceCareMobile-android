package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.navigation.NavHostController
import com.example.icecaremobile.data.local.auth.AuthManagerImpl
import com.example.icecaremobile.domain.model.Response.CompanyAccounts
import com.example.icecaremobile.domain.model.Response.LoginResponseData
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.TransferUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransferScreen(navController: NavHostController)
{
    val authManager = AuthManagerImpl(LocalContext.current)
    var userData by remember { mutableStateOf<LoginResponseData?>(null) }

    LaunchedEffect(Unit) {
        userData = authManager.getLoginResponse()?.data
    }

    Scaffold(
        topBar = { AppTopBar(title = "Transfer"){ navController.navigateUp() }}
    ) { padding ->

        val context = LocalContext.current
        var selectedBankDetails by remember { mutableStateOf<CompanyAccounts?>(null) }
        var enteredDollarAmount by remember { mutableStateOf("") }
        var enteredNairaAmount by remember { mutableStateOf("") }
//        val calculatedDollarEquivalence by remember(enteredNairaAmount) {
//            mutableStateOf(
//                if (enteredNairaAmount.isNotEmpty()) {
//                    try {
//                        BigDecimal(enteredNairaAmount).divide(
//                            BigDecimal(userData?.dollarRate ?: 1.0),
//                            3,
//                            RoundingMode.HALF_UP
//                        )
//                    } catch (e: Exception) {
//                        BigDecimal.ZERO
//                    }
//                } else BigDecimal.ZERO
//            )
//        }
        var enteredPurpose by remember { mutableStateOf("") }
        var boxCheck by remember { mutableStateOf(false) }
        var fieldErrors by remember { mutableStateOf(mapOf<String, String>()) }

        TransferUI(
            modifier = Modifier.padding(padding),
            accounts = userData?.userAccount?.companyAccounts ?: emptyList(),
            enteredDollar = { enteredDollarAmount = it },
            nairaAmount = { enteredNairaAmount = it },
            purpose = { enteredPurpose = it },
            selectedBank = { selectedBankDetails = it },
            isTermsChecked = boxCheck,
            onTermsCheckedChange = { boxCheck = it },
            onButtonClick =
            {
                val errors = validateFields(enteredNairaAmount, enteredPurpose, selectedBankDetails == null).toMutableMap()

                if (errors.isEmpty()) {
                    if (!boxCheck)
                        Toast.makeText(context, "You must agree to the terms and conditions to proceed", Toast.LENGTH_SHORT).show()
                    else {
                        navController.navigate(
                            Screen.TransferSummaryScreen(
                                selectedBankDetails?.bankName,
                                selectedBankDetails?.accountName,
                                selectedBankDetails?.accountNumber,
                                enteredNairaAmount, enteredDollarAmount,
                                email = userData?.email,
                                dollarRate = userData?.userAccount?.dollarRate.toString(),
                                description = enteredPurpose
                            )
                        )
                    }
                }
                else fieldErrors = errors
            },
            isError = { fieldErrors }
        )
    }
}

private fun validateFields(
    nairaAmount: String, description: String, bankSelected: Boolean
): Map<String, String> {
    val errors = mutableMapOf<String, String>()
    if (nairaAmount.isEmpty())
        errors["nairaAmount"] = "Amount in Naira is required."
    if (description.isEmpty())
        errors["description"] = "Enter transfer description"
    if (bankSelected)
        errors["bankSelected"] = "Select bank for transfer"

    return errors
}