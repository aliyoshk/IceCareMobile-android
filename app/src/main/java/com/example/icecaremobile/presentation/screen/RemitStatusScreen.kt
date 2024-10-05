package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.RemitStatusUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RemitStatusScreen(
    navController: NavController,
    key: String?,
) {
    var flag by remember{ mutableStateOf(false) }
    var screenHeader by remember { mutableStateOf("") }

    if (key == "isTransferStatus") {
        flag = true
        screenHeader = "Transfer Status"
    }
    else if (key == "isRemitStatus")
        screenHeader = "Remit Status"

    Scaffold(
        topBar = { AppTopBar(title = screenHeader, {}) },
        modifier = Modifier
            .fillMaxWidth()
    ) { padding ->

        RemitStatusUI(
            modifier = Modifier.padding(padding),
            isSuccess = flag,
            btnProceed = { navController.navigate(Screen.RemitStatusScreen) },
            onDownloadClick = {}
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RemitStatusScreenPreview()
{
    val navController = rememberNavController()
    RemitStatusScreen(navController, "")
}