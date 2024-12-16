package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import com.example.icecaremobile.core.utils.Helpers.convertUriToBase64
import com.example.icecaremobile.data.remote.entity.TransferResponseState
import com.example.icecaremobile.domain.model.Request.BankDetail
import com.example.icecaremobile.domain.model.Request.TransferEvidence
import com.example.icecaremobile.domain.model.Request.TransferRequest
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.TransferSummaryUI
import com.example.icecaremobile.presentation.ui.component.AcceptDialog
import com.example.icecaremobile.presentation.ui.component.AppLoader
import com.example.icecaremobile.presentation.ui.component.AppTopBar
import com.example.icecaremobile.presentation.viewmodel.PaymentViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransferSummaryScreen(
    navController: NavHostController, accounts: Screen.TransferSummaryScreen
) {
    Scaffold(topBar = { AppTopBar(title = "Transfer Summary") }) { padding ->

        val context = LocalContext.current
        val paymentViewModel: PaymentViewModel = hiltViewModel()
        val transferState = paymentViewModel.transferResponse.collectAsState()
        var receipt by remember { mutableStateOf<Uri?>(null) }
        val onSubmitClick = remember { mutableStateOf(false) }

        TransferSummaryUI(
            modifier = Modifier.padding(padding),
            amountSent = accounts.amount ?: "",
            dollarEquivalent = accounts.dollarAmount ?: "",
            bankName = accounts.bankName ?: "",
            accountName = accounts.accountName ?: "",
            accountNumber = accounts.accountNumber ?: "",
            date = LocalDate.now().toString(),
            uploadedReceipt = { receipt = it },
            onSubmitClick = {
                if (receipt == null)
                    Toast.makeText(context, "Upload transfer receipt to continue", Toast.LENGTH_SHORT).show()
                else {
                    val transferRequest = TransferRequest(
                        transactionDate = LocalDate.now().toString(),
                        description = "",
                        dollarAmount = accounts.dollarAmount?.toDoubleOrNull() ?: 0.0,
                        dollarRate = accounts.dollarRate?.toDoubleOrNull() ?: 0.0,
                        customerEmail = accounts.email ?: "",
                        bankDetails = listOf(
                            BankDetail(
                                bankName = accounts.bankName ?: "",
                                transferredAmount = accounts.amount?.toDoubleOrNull() ?: 0.0,
                                accountNumber = accounts.accountNumber ?: "",
                                accountName = accounts.accountName ?: ""
                            )
                        ),
                        transferEvidence = listOfNotNull(TransferEvidence(convertUriToBase64(context, receipt!!) ?: ""))
                    )
                    paymentViewModel.fundTransfer(transferRequest)
                    onSubmitClick.value = true
                }
            }
        )

        if (onSubmitClick.value) {
            RenderTransferState(transferState.value, navController)
        }
    }
}

@Composable
fun RenderTransferState(
    state: TransferResponseState,
    navController: NavHostController
) {
    var showDialog by remember { mutableStateOf(true) }
    when (state) {
        is TransferResponseState.Loading -> {
            showDialog = true
            AppLoader()
        }
        is TransferResponseState.Success -> {
            LaunchedEffect(Unit) {
                val message = state.transferResponse.message
                navController.navigate( Screen.SubmissionScreen(data = message, key = Screen.TransferSummaryScreen.toString()))
            }
        }
        is TransferResponseState.Error -> {
            if (showDialog) {
                AcceptDialog(
                    title = "Error",
                    message = state.message,
                    buttonText = "Okay",
                    onButtonClick = { showDialog = false },
                    onDismissRequest = { showDialog = false }
                )
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TransferSummaryScreenPreview()
{
    val navController = rememberNavController()
    TransferSummaryScreen(navController, accounts = Screen.TransferSummaryScreen("", "", "", "", ""))
}