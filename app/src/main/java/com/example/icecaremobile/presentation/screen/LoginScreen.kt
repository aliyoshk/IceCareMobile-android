package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.data.remote.entity.LoginResponseState
import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.LoginUI
import com.example.icecaremobile.presentation.ui.component.AcceptDialog
import com.example.icecaremobile.presentation.ui.component.AppLoader
import com.example.icecaremobile.presentation.ui.component.AppTopBar
import com.example.icecaremobile.presentation.viewmodel.LoginViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel: LoginViewModel = hiltViewModel()
    val response by viewModel.loginResponse.collectAsState()

    var buttonClick by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = { AppTopBar(title = "Login") }
    ) { padding ->

        LoginUI(
            modifier = Modifier.padding(padding),
            username = { email = it },
            password = { password = it },
            onSignUpClick = { navController.navigate(Screen.RegistrationScreen) },
            onForgotPasswordClick = {},
            btnLogin = {
                viewModel.login(LoginRequest(email, password))
                buttonClick = true
            }
        )

        if (buttonClick) {
            when (val currentResponse = response) {
                is LoginResponseState.Loading -> {
                    AppLoader()
                }
                is LoginResponseState.Success -> {
                    buttonClick = false
                    navController.navigate(Screen.DashboardScreen)
                }
                is LoginResponseState.Error -> {
                    AcceptDialog(
                        title = "Error",
                        message = currentResponse.message,
                        buttonText = "Okay",
                        onButtonClick = { buttonClick = false },
                        onDismissRequest = { buttonClick = false }
                    )
                }
            }
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