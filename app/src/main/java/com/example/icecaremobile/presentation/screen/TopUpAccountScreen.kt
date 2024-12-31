package com.example.icecaremobile.presentation.screen

import android.net.Uri
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.icecaremobile.core.utils.Helpers
import com.example.icecaremobile.data.local.auth.AuthManagerImpl
import com.example.icecaremobile.domain.model.Request.BankDetail
import com.example.icecaremobile.domain.model.Request.TopUpRequest
import com.example.icecaremobile.domain.model.Request.TransferEvidence
import com.example.icecaremobile.domain.model.Response.CompanyAccounts
import com.example.icecaremobile.domain.model.Response.LoginResponseData
import com.example.icecaremobile.presentation.ui.TopUpAccountUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar
import com.example.icecaremobile.presentation.viewmodel.PaymentViewModel


@Composable
fun TopUpAccountScreen(
    navController: NavHostController,
    viewModel: PaymentViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val transferState = viewModel.transferResponse.collectAsState()

    val authManager = AuthManagerImpl(LocalContext.current)
    var userData by remember { mutableStateOf<LoginResponseData?>(null) }
    var bankList by remember { mutableStateOf<List<CompanyAccounts>?>(null) }

    var fieldErrors by remember { mutableStateOf(mapOf<String, String>()) }
    val onSubmitClick = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        userData = authManager.getLoginResponse()?.data
        bankList = authManager.getBankResponse()
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = { AppTopBar("Top Up Account") { navController.navigateUp() } }
    ) { padding ->
        var selectedBanks by remember { mutableStateOf<List<CompanyAccounts>>(emptyList()) }
        var bankAmounts by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
        var purposeOfPayment by remember { mutableStateOf("") }
        var uploadedReceipts by remember { mutableStateOf<List<Uri>>(emptyList()) }

        TopUpAccountUI(
            modifier = Modifier.padding(padding),
            banks = bankList ?: emptyList(),
            purpose = { purposeOfPayment = it },
            selectedBanks = { selectedBanks = it },
            enteredAmounts = { bankAmounts = it },
            receiptUploaded = { uploadedReceipts = it },
            onSubmitClick = {
                val errors = validateFields(
                    bankAmounts = bankAmounts,
                    purposeOfPayment = purposeOfPayment,
                    selectedBanks = selectedBanks,
                    uploadedReceipts = uploadedReceipts
                ).toMutableMap()

                if (errors.isEmpty()) {
                    val bankDetails = selectedBanks.map { bank ->
                        BankDetail(
                            bankName = bank.bankName,
                            transferredAmount = bankAmounts[bank.bankName]?.toDoubleOrNull() ?: 0.0,
                            accountName = bank.accountName,
                            accountNumber = bank.accountNumber
                        )
                    }
                    val transferEvidence = uploadedReceipts.mapNotNull { uri ->
                        Helpers.convertUriToBase64(context, uri)?.let { base64String ->
                            TransferEvidence(receipts = base64String)
                        }
                    }
                    val request = TopUpRequest(
                        description = purposeOfPayment,
                        currency = "Naira",
                        accountNo = userData?.accountNumber ?: "",
                        email = userData?.email ?: "",
                        phone = userData?.phone ?: "",
                        bankDetails = bankDetails,
                        transferEvidence = transferEvidence
                    )
                    viewModel.accountTopUp(request)
                    onSubmitClick.value = true
                }
                else fieldErrors = errors
            },
            isError = { fieldErrors }
        )

        if (onSubmitClick.value) {
            RenderTransferState(transferState.value, navController)
        }
    }
}

private fun validateFields(
    bankAmounts: Map<String, String>,
    purposeOfPayment: String,
    selectedBanks: List<CompanyAccounts>,
    uploadedReceipts: List<Uri>
): Map<String, String> {
    val errors = mutableMapOf<String, String>()

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