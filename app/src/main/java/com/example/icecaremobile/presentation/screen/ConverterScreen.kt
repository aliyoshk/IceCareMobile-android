package com.example.icecaremobile.presentation.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.data.local.auth.AuthManagerImpl
import com.example.icecaremobile.domain.model.Response.LoginResponseData
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.ConverterUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar
import java.math.BigDecimal

@Composable
fun ConverterScreen(navController: NavHostController) {

    val authManager = AuthManagerImpl(LocalContext.current)
    var userData by remember { mutableStateOf<LoginResponseData?>(null) }
    var enteredAmount by remember { mutableStateOf("") }
    val totalAmount by remember(enteredAmount) {
        mutableStateOf(
            if (enteredAmount.isNotEmpty()) {
                BigDecimal(enteredAmount).multiply(BigDecimal(userData?.dollarRate!!))
            } else BigDecimal.ZERO
        )
    }

    LaunchedEffect(Unit) {
        userData = authManager.getLoginResponse()?.data
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = { AppTopBar("Calculator"){ navController.navigateUp() }}
    ) { padding ->

        ConverterUI(
            modifier = Modifier.padding(padding),
            dollarRate = userData?.dollarRate.toString(),
            amountEntered = { enteredAmount = it },
            onCancelPress = { navController.navigateUp() },
            onProceedClick = { navController.navigate(Screen.SendMoneyScreen) },
            total = totalAmount
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ConverterScreenPreview() {
    val navController = rememberNavController()
    ConverterScreen(navController)
}