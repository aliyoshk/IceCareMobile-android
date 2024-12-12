package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.data.local.auth.AuthManagerImpl
import com.example.icecaremobile.domain.model.Request.AccountPaymentRequest
import com.example.icecaremobile.domain.model.Response.Response
import com.example.icecaremobile.presentation.ui.AccountBalanceTransferUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar
import com.example.icecaremobile.presentation.viewmodel.PaymentViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccountBalanceTransferScreen(
    navController: NavHostController,
    paymentViewModel: PaymentViewModel = hiltViewModel()
) {
    val transferState = paymentViewModel.transferResponse.collectAsState()
    val authManager = AuthManagerImpl(LocalContext.current)
    var userData by remember { mutableStateOf<Response?>(null) }

    LaunchedEffect(Unit) {
        userData = authManager.getLoginResponse()?.data
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { AppTopBar(title = "Pay from account balance")}
    ) { padding ->

        val context = LocalContext.current
        var enteredNairaAmount by remember { mutableStateOf("") }
        val calculatedDollarEquivalence by remember(enteredNairaAmount) {
            mutableStateOf(
                if (enteredNairaAmount.isNotEmpty()) {
                    (enteredNairaAmount.toDoubleOrNull() ?: 0.0) * (userData?.dollarRate ?: 0.0)
                } else 0.0
            )
        }
        var enteredDescription by remember { mutableStateOf("") }
        var boxCheck by remember { mutableStateOf(false) }
        var fieldErrors by remember { mutableStateOf(mapOf<String, String>()) }
        val onSubmitClick = remember { mutableStateOf(false) }

        AccountBalanceTransferUI(
            modifier = Modifier.padding(padding),
            nairaAmountEntered = { enteredNairaAmount = it },
            dollarEquivalence = calculatedDollarEquivalence.toString(),
            description = { enteredDescription = it },
            isTermsChecked = boxCheck,
            onTermsCheckedChange = { boxCheck = it },
            buttonClicked = {
                val accountPaymentRequest = AccountPaymentRequest(
                    dollarAmount = calculatedDollarEquivalence,
                    customerEmail = userData?.email ?: "",
                    nairaAmount = if (enteredNairaAmount.isNotEmpty()) enteredNairaAmount.toDouble() else 0.0,
                    description = enteredDescription
                )
                val errors = validateFields(accountPaymentRequest).toMutableMap()

                if (errors.isEmpty()) {
                    if (!boxCheck)
                        Toast.makeText(context, "You must agree to the terms and conditions to proceed", Toast.LENGTH_SHORT).show()
                    else {
                        Log.d("OnClickedIssue", accountPaymentRequest.toString())
                        Log.d("OnClickedIssue", paymentViewModel.accountTransfer(accountPaymentRequest).toString())
                        paymentViewModel.accountTransfer(accountPaymentRequest)
                        Log.d("OnClickedIssue", accountPaymentRequest.toString())
                        onSubmitClick.value = true
                    }
                }
                else fieldErrors = errors
            },
            isError = { fieldErrors }
        )

        if (onSubmitClick.value) {
            Log.d("OnClickedIssue", transferState.value.toString())
            RenderTransferState(transferState.value, navController)
            Log.d("OnClickedIssue", transferState.value.toString())
        }
    }
}

private fun validateFields(request: AccountPaymentRequest): Map<String, String> {
    val errors = mutableMapOf<String, String>()

    if (request.dollarAmount <= 0)
        errors["nairaAmount"] = "Amount in Naira is required."
    if (request.description.isEmpty())
        errors["description"] = "Enter transfer description"

    return errors
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountBalanceTransferScreenPreview(){
    val navController = rememberNavController()
    AccountBalanceTransferScreen(navController)
}