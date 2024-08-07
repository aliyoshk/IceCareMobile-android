package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.LoginUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavHostController)
{
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(title = "", navigateBack = {})
        }
    )
    { innerpadding ->

        val context = LocalContext.current
        var buttonClick by remember { mutableStateOf(false) }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        LoginUI(
            username = { email = it },
            password = { password = it },
            btnLogin = { buttonClick = true }
        )

        if (buttonClick)
        {
            buttonClick = false
            Toast.makeText(context, "username value is $email", Toast.LENGTH_SHORT).show()
            Toast.makeText(context, "Password value is $password", Toast.LENGTH_SHORT).show()

            navController.navigate(Screen.DashboardScreen.route)
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview()
{
    val navController = rememberNavController()
    LoginScreen(navController)
}