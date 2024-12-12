package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

        if (buttonClick)
            LoginResponseHandler(response, navController)
    }
}

@Composable
fun LoginResponseHandler(
    response: LoginResponseState,
    navController: NavHostController
) {
    var showDialog by remember { mutableStateOf(true) }
    when (response) {
        is LoginResponseState.Loading -> {
            showDialog = true
            AppLoader()
        }
        is LoginResponseState.Success -> {
            LaunchedEffect(Unit) {
                Log.d("LoginResponseHandler", "Success")
                Log.d("LoginResponseHandler", response.loginResponse.message)
                Log.d("LoginResponseHandler", response.loginResponse.toString())
                val message = response.loginResponse.message
                navController.navigate( Screen.DashboardScreen )
            }
        }
        is LoginResponseState.Error -> {
            if (showDialog) {
                AcceptDialog(
                    title = "Error",
                    message = response.message,
                    buttonText = "Okay",
                    onButtonClick = { showDialog = false },
                    onDismissRequest = { showDialog = false }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController)
}