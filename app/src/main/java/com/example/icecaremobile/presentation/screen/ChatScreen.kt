package com.example.icecaremobile.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.icecaremobile.presentation.ui.ChatUI


@Composable
fun ChatScreen(navController: NavController) {

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->

        ChatUI(modifier = Modifier.padding(padding))
    }
}