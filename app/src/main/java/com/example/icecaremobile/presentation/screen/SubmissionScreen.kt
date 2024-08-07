package com.example.icecaremobile.presentation.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.SubmissionUI

@Composable
fun SubmissionScreen(
    navController: NavController,
    route: String?,
    message: String?
) {
    var color = Color.Black
    var screen = Screen.DashboardScreen.route

    Scaffold(modifier = Modifier.fillMaxWidth()) { padding ->

        if (route == Screen.RegistrationScreen.route)
        {
            color = Color.Red.copy(alpha = 0.8f)
            screen = Screen.LoginScreen.route
        }

        SubmissionUI(
            modifier = Modifier.padding(padding),
            bodyText = message ?: "",
            textColor = color,
            onButtonClick =
            {
                navController.navigate(screen)
            }
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SuccessScreenPreview(){
    val navController = rememberNavController()
    SubmissionScreen(
        navController = navController,
        route = "",
        message = ""
    )
}