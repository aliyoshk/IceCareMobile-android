package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.data.remote.entity.LoginResponseState
import com.example.icecaremobile.domain.auth.AuthManager
import com.example.icecaremobile.domain.model.Request.Device
import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Response.LoginResponse
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.LoginUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar
import com.example.icecaremobile.presentation.viewmodel.LoginViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel: LoginViewModel = hiltViewModel()
    val response by viewModel.loginResponse.collectAsState()
    var buttonClick by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(title = "Login", {})
        }
    ) { padding ->

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        LoginUI(
            modifier = Modifier.padding(padding),
            username = { email = it },
            password = { password = it },
            btnLogin = {
                viewModel.login(getLoginRequest(email, password))
                buttonClick = true
            }
        )

        // Display loading indicator when button is clicked and response is loading
        if (buttonClick) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (val currentResponse = response) {
                    is LoginResponseState.Loading -> {
                        CircularProgressIndicator()
                    }
                    is LoginResponseState.Success -> {
                        buttonClick = false
                        navController.navigate(Screen.DashboardScreen.route)
                    }
                    is LoginResponseState.Error -> {
                        buttonClick = false
                        AlertDialog(
                            onDismissRequest = {},
                            title = { Text(text = "Error") },
                            text = { Text(text = currentResponse.message) },
                            confirmButton = {
                                Button(onClick = { /* No action needed */ }) {
                                    Text("OK")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

private fun getLoginRequest(username: String, password: String): LoginRequest {
    return LoginRequest(
        username = username,
        password = password,
        device = Device(
            imei = "",
            deviceName = "",
            os = "",
            isActiveDevice = false,
            code = "",
            biometricCode = "",
            biometricToken = ""
        )
    )
}

//@Composable
//fun Login(
//    viewModel: LoginViewModel = hiltViewModel(),
//    username: String,
//    password: String,
//    onSuccess: () -> Unit
//)
//{
//    val context = LocalContext.current
//    val response by viewModel.loginResponse.collectAsState()
//
//    LaunchedEffect(username, password) {
//        viewModel.login(
//            loginRequest = LoginRequest(
//                username = username,
//                password = password,
//                device = Device(
//                    imei = "",
//                    deviceName = "",
//                    os = "",
//                    isActiveDevice = false,
//                    code = "",
//                    biometricCode = "",
//                    biometricToken = ""
//                )
//            )
//        )
//    }
//
//    when (val currentResponse = response)
//    {
//        is LoginResponseState.Loading -> {
//            CircularProgressIndicator()
//        }
//
//        is LoginResponseState.Success -> {
//            OnSuccess(currentResponse.loginResponse)
//            //authManager.saveLoginResponse(currentState.loginResponse)
//            println(loginResponse.userProfile.name)
//            println(loginResponse.userProfile.phoneNumber)
//        }
//
//        is LoginResponseState.Error -> {
//            AlertDialog(
//                onDismissRequest = { viewModel.clearError() },
//                title = { Text(text = "Error") },
//                text = { Text(text = currentResponse.message) },
//                confirmButton = {
//                    Button(onClick = { viewModel.clearError() }) {
//                        Text("OK")
//                    }
//                }
//            )
//        }
//    }
//}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview()
{
    val navController = rememberNavController()
    LoginScreen(navController)
}