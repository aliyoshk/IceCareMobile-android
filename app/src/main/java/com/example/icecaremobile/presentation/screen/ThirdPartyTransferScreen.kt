package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.icecaremobile.data.local.auth.AuthManagerImpl
import com.example.icecaremobile.domain.model.Request.ThirdPartyRequest
import com.example.icecaremobile.domain.model.Response.LoginResponseData
import com.example.icecaremobile.presentation.ui.ThirdPartyTransferUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar
import com.example.icecaremobile.presentation.viewmodel.PaymentViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ThirdPartyTransferScreen(
    navController: NavHostController,
    paymentViewModel: PaymentViewModel = hiltViewModel()
) {
    val transferState = paymentViewModel.transferResponse.collectAsState()
    val authManager = AuthManagerImpl(LocalContext.current)
    var userData by remember { mutableStateOf<LoginResponseData?>(null) }

    LaunchedEffect(Unit) {
        userData = authManager.getLoginResponse()?.data
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppTopBar(title = "Transfer to third party"){ navController.navigateUp() }}
    ) { padding ->

        val context = LocalContext.current
        var enteredAmount by remember { mutableStateOf("") }
        var enteredBankName by remember { mutableStateOf("") }
        var enteredAccountNumber by remember { mutableStateOf("") }
        var enteredAccountName by remember { mutableStateOf("") }
        var enteredDescription by remember { mutableStateOf("") }
        var boxCheck by remember { mutableStateOf(false) }
        var fieldErrors by remember { mutableStateOf(mapOf<String, String>()) }
        val onSubmitClick = remember { mutableStateOf(false) }

        ThirdPartyTransferUI(
            modifier = Modifier.padding(padding),
            amount = { enteredAmount = it },
            bankName = { enteredBankName = it },
            accountNumber = { enteredAccountNumber = it },
            accountName = { enteredAccountName = it },
            description = { enteredDescription = it },
            isTermsChecked = boxCheck,
            onTermsCheckedChange = { boxCheck = it },
            submitClicked= {
               val request = ThirdPartyRequest(
                   amount = if (enteredAmount.isNotEmpty()) enteredAmount.toDouble() else 0.0,
                   accountNumber = enteredAccountNumber,
                   accountName = enteredAccountName,
                   bankName = enteredBankName,
                   description = enteredDescription,
                   customerEmail = userData?.email ?: ""
               )
                val errors = validateFields(request).toMutableMap()
                if (errors.isEmpty()) {
                    if (!boxCheck)
                        Toast.makeText(context, "You must agree to the terms and conditions to proceed", Toast.LENGTH_SHORT).show()
                    else {
                        paymentViewModel.thirdPartyTransfer(request)
                        onSubmitClick.value = true
                    }
                }
                else fieldErrors = errors
            },
            isError = { fieldErrors }
        )

        if (onSubmitClick.value)
            RenderTransferState(transferState.value, navController)
    }
}

private fun validateFields(request: ThirdPartyRequest): Map<String, String> {
    val errors = mutableMapOf<String, String>()

    if (request.amount <= 0)
        errors["amount"] = "Amount is required."
    if (request.bankName.isEmpty())
        errors["bankName"] = "Bank is required."
    if (request.accountNumber.isEmpty() || request.accountNumber.length != 10)
        errors["accountNumber"] = "Account Number should be 10 digits long"
    if (request.accountName.isEmpty())
        errors["accountName"] = "Account Name is required."
    if (request.description.isEmpty())
        errors["description"] = "Enter transfer description"

    return errors
}