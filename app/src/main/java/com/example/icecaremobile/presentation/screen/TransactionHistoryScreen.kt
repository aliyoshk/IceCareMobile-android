package com.example.icecaremobile.presentation.screen

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
import androidx.navigation.NavController
import com.example.icecaremobile.data.local.auth.AuthManagerImpl
import com.example.icecaremobile.data.remote.entity.TransactionHistoryResponseState
import com.example.icecaremobile.domain.model.Response.LoginResponseData
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.TransactionHistoryUI
import com.example.icecaremobile.presentation.ui.component.AcceptDialog
import com.example.icecaremobile.presentation.ui.component.AppLoader
import com.example.icecaremobile.presentation.ui.component.AppTopBar
import com.example.icecaremobile.presentation.viewmodel.TransactionViewModel

@Composable
fun TransactionHistoryScreen(
    navController: NavController,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val response by viewModel.historyResponse.collectAsState()
    val authManager = AuthManagerImpl(LocalContext.current)
    var userData by remember { mutableStateOf<LoginResponseData?>(null) }
    var showDialog by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        userData = authManager.getLoginResponse()?.data
        if (userData?.email != null) {
            viewModel.getTransactionHistory(userData?.email!!)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(title = "Transaction History", navigateBack = { navController.navigateUp() })
        }
    ) { padding ->

        when (val obj = response) {
            is TransactionHistoryResponseState.Loading -> {
                AppLoader()
            }
            is TransactionHistoryResponseState.Success -> {
                TransactionHistoryUI(
                    modifier = Modifier.padding(padding),
                    searchText = { },
                    historyData = obj.historyResponseState.data,
                    onItemSelection = {
                        navController.navigate(Screen.ReceiptScreen(it))
                    }
                )
            }
            is TransactionHistoryResponseState.Error -> {
                if (showDialog) {
                    AcceptDialog(
                        title = "Error",
                        message = obj.message,
                        buttonText = "Okay",
                        onButtonClick = { showDialog = false; navController.popBackStack() },
                        onDismissRequest = { showDialog = false }
                    )
                }
            }
        }
    }
}