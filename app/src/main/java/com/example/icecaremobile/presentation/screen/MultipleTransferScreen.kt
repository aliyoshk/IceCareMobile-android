package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.icecaremobile.core.utils.Helpers
import com.example.icecaremobile.data.local.auth.AuthManagerImpl
import com.example.icecaremobile.domain.model.Request.BankDetail
import com.example.icecaremobile.domain.model.Request.TransferEvidence
import com.example.icecaremobile.domain.model.Request.TransferRequest
import com.example.icecaremobile.domain.model.Response.CompanyAccounts
import com.example.icecaremobile.domain.model.Response.Response
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.MultipleTransferUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar
import com.example.icecaremobile.presentation.viewmodel.PaymentViewModel
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MultipleTransferScreen(navController: NavHostController)
{
    val context = LocalContext.current
    val paymentViewModel: PaymentViewModel = hiltViewModel()
    val transferState = paymentViewModel.transferResponse.collectAsState()

    val authManager = AuthManagerImpl(LocalContext.current)
    var userData by remember { mutableStateOf<Response?>(null) }
    var bankList by remember { mutableStateOf<List<CompanyAccounts>?>(null) }

    val destination = Screen.TransferSummaryScreen
    var fieldErrors by remember { mutableStateOf(mapOf<String, String>()) }
    val onSubmitClick = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        userData = authManager.getLoginResponse()?.data
        bankList = authManager.getBankResponse()
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = { AppTopBar("Multiple Transfer") }
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
                val errors = validateFields(
                    dollarAmount = dollarAmount,
                    bankAmounts = bankAmounts,
                    purposeOfPayment = purposeOfPayment,
                    selectedBanks = selectedBanks,
                    uploadedReceipts = uploadedReceipts
                ).toMutableMap()

                if (errors.isEmpty()) {
                    val bankDetails = selectedBanks.map { bank ->
                        BankDetail(
                            bankName = bank.bankName,
                            transferredAmount = bankAmounts[bank.bankName]?.toDoubleOrNull() ?: 0.0
                        )
                    }
                    val transferEvidence = uploadedReceipts.mapNotNull { uri ->
                        Helpers.convertUriToBase64(context, uri)?.let { base64String ->
                            TransferEvidence(receipts = base64String)
                        }
                    }
                    val transferRequest = TransferRequest(
                        transactionDate = LocalDate.now().toString(),
                        description = purposeOfPayment,
                        dollarAmount = dollarAmount.toDoubleOrNull() ?: 0.0,
                        dollarRate = userData?.dollarRate?.toDouble() ?: 0.0,
                        customerEmail = userData?.email ?: "",
                        bankDetails = bankDetails,
                        transferEvidence = transferEvidence
                    )
                    paymentViewModel.fundTransfer(transferRequest)

                    //navController.navigate( Screen.SubmissionScreen(key = destination.toString(), data = ""))
                    onSubmitClick.value = true
                }
                else fieldErrors = errors
            },
            isError = { fieldErrors }
        )

        if (onSubmitClick.value) {
            RenderTransferState(transferState.value, navController, paymentViewModel)
        }
    }
}

private fun validateFields(
    dollarAmount: String,
    bankAmounts: Map<String, String>,
    purposeOfPayment: String,
    selectedBanks: List<CompanyAccounts>,
    uploadedReceipts: List<Uri>
): Map<String, String> {
    val errors = mutableMapOf<String, String>()

    if (dollarAmount.isBlank())
        errors["dollarAmount"] = "Amount in Dollar is required."
    if (purposeOfPayment.isBlank())
        errors["purpose"] = "Purpose of payment is required."
    if (selectedBanks.isEmpty())
        errors["selectedBanks"] = "Please select at least one bank."

    selectedBanks.forEach { bank ->
        val amount = bankAmounts[bank.bankName]
        if (amount.isNullOrBlank())
            errors[bank.bankName] = "Amount is required for ${bank.bankName}."
        else if (uploadedReceipts.size < selectedBanks.size)
            errors["uploadedReceipts"] = "Upload receipt(s) for the ${selectedBanks.size} selected banks."
    }
    return errors
}




@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MultipleTransferScreenPreview()
{
    val navController = rememberNavController()
    MultipleTransferScreen(navController)
}