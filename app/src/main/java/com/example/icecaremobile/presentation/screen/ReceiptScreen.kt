package com.example.icecaremobile.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.icecaremobile.domain.model.Response.TransactionHistory
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.ReceiptUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar
import com.example.icecaremobile.presentation.ui.component.generateReceiptPDF
import com.example.icecaremobile.presentation.viewmodel.TransactionViewModel


@Composable
fun ReceiptScreen(
    navController: NavController,
    viewModel: TransactionViewModel = hiltViewModel(),
    transactionHistory: TransactionHistory
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(navigateBack = { navController.navigateUp() })
        }
    ) { padding ->
        val context = LocalContext.current

        ReceiptUI(
            modifier = Modifier.padding(padding),
            goToDashboard = { navController.navigate(Screen.DashboardScreen) },
            data = transactionHistory,
            downloadReceipt = {
                generateReceiptPDF(context, onComplete = {
                    Toast.makeText(context, "Receipt saved successfully!", Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = "Your receipt content goes here")
                }
            }
        )
    }
}