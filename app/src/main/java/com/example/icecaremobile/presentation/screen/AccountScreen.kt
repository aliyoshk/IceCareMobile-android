package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.AccountUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccountScreen(navController: NavHostController)
{
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppTopBar("Available Account", {}) }
    ){ padding ->

        var boxChecked by remember{ mutableStateOf(false) }
        var btnProceed by remember { mutableStateOf(false) }

        AccountUI(
            modifier = Modifier.padding(padding),
            onCheck = { boxChecked = true },
            onButtonClick = {
                btnProceed = true
                navController.navigate(Screen.MultipleTransferScreen.route)
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountScreenPreview()
{
    val navController = rememberNavController()
    AccountScreen(navController)
}