package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.data.remote.entity.RegistrationResponseState
import com.example.icecaremobile.domain.model.Request.RegistrationRequest
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.RegistrationUI
import com.example.icecaremobile.presentation.ui.component.AcceptDialog
import com.example.icecaremobile.presentation.ui.component.AppLoader
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
            AppTopBar(title = "")
        }
    ) { padding ->
        val context = LocalContext.current
        var buttonClick by remember { mutableStateOf(false) }
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var isTermsChecked by remember { mutableStateOf(false) }
        var fieldErrors by remember { mutableStateOf(mapOf<String, String>()) }

        RegistrationUI(
            modifier = Modifier.padding(padding),
            fullName = { name = it },
            email = { email = it },
            phoneNumber = { phone = it },
            password = { password = it},
            confirmPassword = { confirmPassword = it},
            isTermsChecked = isTermsChecked,
            onTermsCheckedChange = { isTermsChecked = it },
            btnSignUp = {
                val request = RegistrationRequest(
                    fullName = name,
                    email = email,
                    phone = phone,
                    password = password
                )
                val errors = validateRegistrationRequest(request).toMutableMap()

                if (phone.isEmpty())
                    errors["phone"] = "Phone number is required"
                if (confirmPassword.isEmpty())
                    errors["confirmPassword"] = "Confirm password is required"
                else if (password != confirmPassword)
                    errors["confirmPassword"] = "Passwords do not match."

                if (errors.isEmpty()) {
                    if (!isTermsChecked)
                        Toast.makeText(context, "You must agree to the terms and conditions to proceed", Toast.LENGTH_SHORT).show()
                    else {
                        viewModel.registration(request)
                        buttonClick = true
                    }
                }
                else fieldErrors = errors
            },
            isError = { fieldErrors }
        )

        if (buttonClick)
            RegistrationResponseHandler(response, navController)
    }
}

private fun validateRegistrationRequest(request: RegistrationRequest): Map<String, String> {
    val errors = mutableMapOf<String, String>()
    if (request.fullName.isEmpty()) {
        errors["name"] = "Full name is required."
    }
    if (request.email.isEmpty()) {
        errors["email"] = "Email is required."
    }
    if (request.phone.isEmpty()) {
        errors["phoneNumber"] = "Phone number is required."
    }
    if (request.password.isEmpty()) {
        errors["password"] = "Password is required."
    }
    return errors
}

@Composable
fun RegistrationResponseHandler(
    response: RegistrationResponseState,
    navController: NavHostController
) {
    var showDialog by remember { mutableStateOf(true) }
    when (response) {
        is RegistrationResponseState.Loading -> {
            showDialog = true
            AppLoader()
        }
        is RegistrationResponseState.Success -> {
            LaunchedEffect(Unit) {
                val message = response.registrationResponse.data
                navController.navigate( Screen.SubmissionScreen(data = message, key = Screen.RegistrationScreen.toString()))
            }
        }
        is RegistrationResponseState.Error -> {
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
        null -> { showDialog = true }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun RegistrationScreenPreview()
{
    val navController = rememberNavController()
    RegistrationScreen(navController)
}