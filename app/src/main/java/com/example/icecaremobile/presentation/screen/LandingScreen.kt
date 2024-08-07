package com.example.icecaremobile.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.LandingPageUI

@Composable
fun LandingScreen(navController: NavHostController)
{
    LandingPageUI { navController.navigate(Screen.RegistrationScreen.route)}
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LandingScreenPreview()
{
    val navController = rememberNavController()
    LandingScreen(navController)
}