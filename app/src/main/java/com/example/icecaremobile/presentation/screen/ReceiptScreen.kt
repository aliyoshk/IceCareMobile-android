package com.example.icecaremobile.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.icecaremobile.domain.model.Response.TransactionHistory
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.ReceiptUI


@Composable
fun ReceiptScreen(
    navController: NavController,
    transactionHistory: TransactionHistory
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->

        ReceiptUI(
            modifier = Modifier.padding(padding),
            goToDashboard = { navController.navigate(Screen.DashboardScreen) },
            data = transactionHistory
        )
    }
}