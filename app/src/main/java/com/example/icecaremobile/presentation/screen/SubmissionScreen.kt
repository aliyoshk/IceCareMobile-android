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
import com.example.icecaremobile.presentation.ui.SubmissionUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SubmissionScreen(navController: NavController)
{
    Scaffold(modifier = Modifier.fillMaxWidth()) { padding ->

        SubmissionUI(
            modifier = Modifier.padding(padding),
            bodyText = "",
            onButtonClick =
            {
                navController.navigate(Screen.DashboardScreen.route)
            }
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SubmissionScreenPreview(){
    val navController = rememberNavController()
    SubmissionScreen(navController)
}