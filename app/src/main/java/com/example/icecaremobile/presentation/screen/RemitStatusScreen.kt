package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.RemitStatusUI
import com.example.icecaremobile.presentation.ui.RemitStatusUIPreview
import com.example.icecaremobile.presentation.ui.component.AppTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RemitStatusScreen(navController: NavController)
{
    Scaffold(
        topBar = { AppTopBar(title = "Remit Status", {}) },
        modifier = Modifier
            .fillMaxWidth()
    ) { padding ->

        RemitStatusUI(
            modifier = Modifier.padding(padding),
            isSuccess = false,
            btnProceed = { navController.navigate(Screen.RemitStatusScreen.route) },
            onDownloadClick = {}
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RemitStatusScreenPreview()
{
    val navController = rememberNavController()
    RemitStatusScreen(navController)
}