package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.icecaremobile.domain.model.Response.CompanyAccounts
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.AccountUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccountScreen(navController: NavHostController)
{
    val context = LocalContext.current
    val authManager = AuthManagerImpl(LocalContext.current)
    var bankList by remember { mutableStateOf<List<CompanyAccounts>?>(null) }
    var boxChecked by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        bankList = authManager.getBankResponse()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppTopBar(title = "Available Account") { navController.navigateUp() }}
    ) { paddingValues ->

        AccountUI(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            onCheck = { boxChecked = true },
            banks = bankList ?: emptyList(),
            onButtonClick = {
                if (!boxChecked)
                    Toast.makeText(context, "Please check the box to proceed", Toast.LENGTH_SHORT).show()
                else
                    navController.navigate(Screen.MultipleTransferScreen)
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountScreenPreview() {
    val navController = rememberNavController()
    AccountScreen(navController)
}