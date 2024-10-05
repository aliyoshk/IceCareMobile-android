package com.example.icecaremobile.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AcceptDialog(
    title: String?,
    message: String?,
    buttonText: String,
    onButtonClick: () -> Unit,
    onDismissRequest : () -> Unit = {}
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(5.dp)
                .background(Color.White),
        ) {
            Spacer(Modifier.height(50.dp))

            if (title != null) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(8.dp))

            if (message != null) {
                Text(
                    text = message,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Spacer(Modifier.height(27.dp))

            AppButton(
                title = buttonText,
                onClick = { onButtonClick() }
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun AcceptDialogPreview()
{
    AcceptDialog(
        title = "Reset email sent",
        message = "To regain access, please reach out to the admin or click on the \"Forgot Password\" link to reset your password.",
        buttonText = "Okay",
        onButtonClick = {},
        onDismissRequest = {}
    )
}