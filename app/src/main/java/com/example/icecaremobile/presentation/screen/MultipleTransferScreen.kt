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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.icecaremobile.core.utils.Helpers
import com.example.icecaremobile.data.local.auth.AuthManagerImpl
import com.example.icecaremobile.domain.model.Request.BankDetail
import com.example.icecaremobile.domain.model.Request.TransferEvidence
import com.example.icecaremobile.domain.model.Request.TransferRequest
import com.example.icecaremobile.domain.model.Response.CompanyAccounts
import com.example.icecaremobile.domain.model.Response.LoginResponseData
import com.example.icecaremobile.presentation.ui.MultipleTransferUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar
import com.example.icecaremobile.presentation.viewmodel.PaymentViewModel
import java.math.BigDecimal
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MultipleTransferScreen(navController: NavHostController) {
    val context = LocalContext.current
    val paymentViewModel: PaymentViewModel = hiltViewModel()
    val transferState = paymentViewModel.transferResponse.collectAsState()

    val authManager = AuthManagerImpl(LocalContext.current)
    var userData by remember { mutableStateOf<LoginResponseData?>(null) }
    var bankList by remember { mutableStateOf<List<CompanyAccounts>?>(null) }
    var fieldErrors by remember { mutableStateOf(mapOf<String, String>()) }

    LaunchedEffect(Unit) {
        userData = authManager.getLoginResponse()?.data
        bankList = authManager.getBankResponse()
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = { AppTopBar("Multiple Transfer") { navController.navigateUp() }}
    ) { padding ->

        var selectedBanks by remember { mutableStateOf<List<CompanyAccounts>>(emptyList()) }
        var bankAmounts by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
        var purposeOfPayment by remember { mutableStateOf("") }
//        var dollarAmounts by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
//        var dollarAmount by remember { mutableStateOf(BigDecimal.ZERO) }
        var dollarAmount by remember { mutableStateOf("") }
        val totalAmount = bankAmounts.values.fold(BigDecimal.ZERO) { acc, amount -> acc.add(amount.toBigDecimal()) }
        var uploadedReceipts by remember { mutableStateOf<List<Uri>>(emptyList()) }
        val onSubmitClick = remember { mutableStateOf(false) }

//        LaunchedEffect(bankAmounts) {
//            dollarAmounts = bankAmounts.mapValues { (bankName, nairaAmount) ->
//                try {
//                    if (nairaAmount.isNotEmpty()) {
//                        BigDecimal(nairaAmount).divide(
//                            BigDecimal(userData?.dollarRate ?: 1.0), 3, RoundingMode.HALF_UP
//                        ).toPlainString()
//                    } else "0.00"
//                } catch (e: Exception) {
//                    "0.00"
//                }
//            }
//            dollarAmount = dollarAmounts.values.fold(BigDecimal.ZERO) { acc, amount -> acc.add(amount.toBigDecimal()) }
//        }

        MultipleTransferUI(
            modifier = Modifier.padding(padding),
            banks = bankList ?: emptyList(),
            dollarAmount = { dollarAmount = it },
            purpose = { purposeOfPayment = it },
            selectedBanks = { selectedBanks = it },
            enteredAmounts = { bankAmounts = it },
            receiptUploaded = { uploadedReceipts = it },
            totalAmount = totalAmount,
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
                    val transferRequest = TransferRequest(
                        transactionDate = LocalDate.now().toString(),
                        description = purposeOfPayment,
                        dollarAmount = dollarAmount.toDouble(),
                        dollarRate = userData?.dollarRate ?: 0.0,
                        customerEmail = userData?.email ?: "",
                        bankDetails = bankDetails,
                        transferEvidence = transferEvidence
                    )
                    paymentViewModel.fundTransfer(transferRequest)
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