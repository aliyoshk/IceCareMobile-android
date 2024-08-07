package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.icecaremobile.data.remote.entity.RegistrationResponseState
import com.example.icecaremobile.domain.model.Request.RegistrationRequest
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.RegistrationUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar
import com.example.icecaremobile.presentation.viewmodel.RegistrationViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegistrationScreen(navController: NavHostController)
{
    val viewModel: RegistrationViewModel = hiltViewModel()
    val response by viewModel.registrationResponse.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(title = "", navigateBack = {})
        }
    )
    { padding ->
        var buttonClick by remember { mutableStateOf(false) }
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

        RegistrationUI(
            modifier = Modifier.padding(padding),
            fullName = { name = it },
            email = { email = it },
            phoneNumber = { phone = it },
            password = { password = it},
            confirmPassword = { confirmPassword = it},
            btnSignUp = {
                viewModel.registration(
                    registrationRequest(
                        name = name,
                        email = email,
                        phone = phone,
                        password = password
                    )
                )
                buttonClick = true
            }
        )

        if (buttonClick)
            RegistrationResponseHandler(response, navController, buttonState = { buttonClick = it } )
    }
}

private fun registrationRequest(
    name: String, email: String, phone: String, password: String
): RegistrationRequest
{
    return RegistrationRequest(
        name = name,
        email = email,
        phoneNumber = phone,
        password = password
    )
}

@Composable
fun RegistrationResponseHandler(
    response: RegistrationResponseState,
    navController: NavHostController,
    buttonState: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (response) {
            is RegistrationResponseState.Loading -> {
                buttonState(true)
                CircularProgressIndicator()
            }
            is RegistrationResponseState.Success -> {
                buttonState(false)
                println(response.registrationResponse.message)
                println(response.registrationResponse.data)
                val destination = Screen.RegistrationScreen.route
                navController.navigate(
                    Screen.SubmissionScreen.route + "/${response.registrationResponse.message}/${destination}"
                )
            }
            is RegistrationResponseState.Error -> {
                buttonState(false)
                AlertDialog(
                    onDismissRequest = {},
                    title = { Text(text = "Error") },
                    text = { Text(text = response.message) },
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

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun RegistrationScreenPreview()
{
    val navController = rememberNavController()
    RegistrationScreen(navController)
}