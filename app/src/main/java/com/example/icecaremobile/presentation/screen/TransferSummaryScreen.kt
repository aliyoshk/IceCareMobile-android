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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.icecaremobile.core.utils.Helpers.convertUriToBase64
import com.example.icecaremobile.data.local.auth.AuthManagerImpl
import com.example.icecaremobile.data.remote.entity.TransferResponseState
import com.example.icecaremobile.data.remote.entity.UserAccountState
import com.example.icecaremobile.domain.auth.AuthManager
import com.example.icecaremobile.domain.model.Request.BankDetail
import com.example.icecaremobile.domain.model.Request.StatusRequest
import com.example.icecaremobile.domain.model.Request.TransferEvidence
import com.example.icecaremobile.domain.model.Request.TransferRequest
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.TransferSummaryUI
import com.example.icecaremobile.presentation.ui.component.AcceptDialog
import com.example.icecaremobile.presentation.ui.component.AppLoader
import com.example.icecaremobile.presentation.ui.component.AppTopBar
import com.example.icecaremobile.presentation.viewmodel.LoginViewModel
import com.example.icecaremobile.presentation.viewmodel.PaymentViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransferSummaryScreen(
    navController: NavHostController,
    accounts: Screen.TransferSummaryScreen,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val authManager = AuthManagerImpl(LocalContext.current)

    Scaffold(
        topBar = { AppTopBar(title = "Transfer Summary"){ navController.navigateUp() }}
    ) { padding ->

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

        if (onSubmitClick.value)
            RenderTransferState(transferState.value, navController, authManager, viewModel)
    }
}

@Composable
fun RenderTransferState(
    state: TransferResponseState,
    navController: NavHostController,
    authManager: AuthManager,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    var showDialog by remember { mutableStateOf(true) }
    var isRefreshing by remember { mutableStateOf(false) }
    val refreshResponse by loginViewModel.userAccountResponse.collectAsState()

    when (state) {
        is TransferResponseState.Loading -> {
            showDialog = true
            AppLoader()
        }
        is TransferResponseState.Success -> {
            if (!isRefreshing) {
                // Start account refresh
                LaunchedEffect(Unit) {
                    isRefreshing = true
                    val email = authManager.getLoginResponse()?.data?.email
                    loginViewModel.refreshAccount(StatusRequest(email!!))
                }
            } else {
                // Handle the refresh response
                when (refreshResponse) {
                    is UserAccountState.Loading -> {
                        AppLoader()
                    }
                    is UserAccountState.Success -> {
                        LaunchedEffect(Unit) {
                            val message = state.transferResponse.message
                            val response = (refreshResponse as UserAccountState.Success).userAccount
                            navController.navigate(
                                ( Screen.SubmissionScreen(data = message, key = Screen.TransferSummaryScreen.toString())),
                                builder = {
                                    popUpTo(Screen.DashboardScreen) { inclusive = true }
                                }
                            )
                        }
                    }
                    is UserAccountState.Error -> {
                        if (showDialog) {
                            AcceptDialog(
                                title = "Error",
                                message = (refreshResponse as UserAccountState.Error).message,
                                buttonText = "Retry",
                                onButtonClick = {
                                    showDialog = false
                                    isRefreshing = false
                                    navController.navigate(Screen.LoginScreen)
                                },
                                onDismissRequest = { showDialog = false }
                            )
                        }
                    }
                }
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


//@Composable
//fun RenderTransferState(
//    state: TransferResponseState,
//    navController: NavHostController
//) {
//    var showDialog by remember { mutableStateOf(true) }
//    when (state) {
//        is TransferResponseState.Loading -> {
//            showDialog = true
//            AppLoader()
//        }
//        is TransferResponseState.Success -> {
//            LaunchedEffect(Unit) {
//                val message = state.transferResponse.message
//                navController.navigate( Screen.SubmissionScreen(data = message, key = Screen.TransferSummaryScreen.toString()),
//                    builder = {
//                        popUpTo(Screen.DashboardScreen) { inclusive = true }
//                    }
//                )
//            }
//        }
//        is TransferResponseState.Error -> {
//            if (showDialog) {
//                AcceptDialog(
//                    title = "Error",
//                    message = state.message,
//                    buttonText = "Okay",
//                    onButtonClick = { showDialog = false },
//                    onDismissRequest = { showDialog = false }
//                )
//            }
//        }
//    }
//}

