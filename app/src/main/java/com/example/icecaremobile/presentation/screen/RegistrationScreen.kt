package com.example.icecaremobile.presentation.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.example.icecaremobile.presentation.ui.RegistrationUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegistrationScreen(navController: NavHostController)
{
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(title = "", navigateBack = {})
        }
    )
    { padding ->

        val context = LocalContext.current
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
            phoneNumber = { phone = it.toString() },
            password = { password = it},
            confirmPassword = { confirmPassword = it},
            btnSignUp = { buttonClick = true}
        )

        if (buttonClick)
        {
            buttonClick = false

            Toast.makeText(context, "Name value is $name", Toast.LENGTH_SHORT).show()
            Toast.makeText(context, "email value is $email", Toast.LENGTH_SHORT).show()
            Toast.makeText(context, "Phone value is ${phone.toIntOrNull()}", Toast.LENGTH_SHORT).show()
            Toast.makeText(context, "Password value is $password", Toast.LENGTH_SHORT).show()
            Toast.makeText(context, "confirmPassword value is $confirmPassword", Toast.LENGTH_SHORT).show()

            navController.navigate(Screen.LoginScreen.route)
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