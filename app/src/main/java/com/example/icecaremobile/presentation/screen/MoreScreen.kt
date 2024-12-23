package com.example.icecaremobile.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.icecaremobile.R
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.presentation.ui.MoreUI
import com.example.icecaremobile.presentation.ui.component.AppTopBar

@Composable
fun MoreScreen(navController: NavController) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppTopBar(title = "Get Help") { navController.navigateUp() } }
    ) { padding ->
        val context = LocalContext.current
        var titleOption by remember { mutableStateOf("") }
        var clicked by remember { mutableStateOf(false) }

        val items = listOf(
            Triple(R.drawable.ic_phone, "Call Us", "Contact call center"),
            Triple(R.drawable.ic_chat, "Chat With Us", "Send an in app message"),
            Triple(R.drawable.ic_question, "FAQS", "Frequently asked questions")
        )

        MoreUI(
            modifier = Modifier.padding(padding),
            items = items,
            optionTitle = { titleOption = it },
            onClick = { clicked = true }
        )

        if (clicked) {
            clicked = false
            if (titleOption.contains("Call Us"))
                navController.navigate(Screen.CallScreen)
            else if (titleOption.contains("Chat With Us"))
                Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
            else if (titleOption.contains("FAQS"))
                Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
        }
    }
}