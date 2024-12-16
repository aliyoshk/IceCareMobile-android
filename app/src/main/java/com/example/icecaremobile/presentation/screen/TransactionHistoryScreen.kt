package com.example.icecaremobile.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.icecaremobile.presentation.ui.TransactionHistoryUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar

@Composable
fun TransactionHistoryScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(title = "Transaction History", navigateBack = { navController.navigateUp() })
        }
    ) { padding ->

        TransactionHistoryUI(
            modifier = Modifier.padding(padding),
            searchText = {},
            selectedItem = {},
            onClick = {}
        )
    }
}