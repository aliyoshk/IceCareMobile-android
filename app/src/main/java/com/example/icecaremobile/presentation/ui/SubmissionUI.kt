package com.example.icecaremobile.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.icecaremobile.R
import com.example.icecaremobile.presentation.ui.component.AppButton
import com.example.icecaremobile.ui.theme.AppGolden
import com.example.icecaremobile.ui.theme.LightGolden


@Composable
fun SubmissionUI(
    modifier: Modifier = Modifier,
    bodyText: String,
    textColor: Color = Color.Black,
    onButtonClick: () -> Unit
) {
    val scroll = rememberScrollState()
    val icon =
        if (bodyText.lowercase().contains("transfer")) R.drawable.ic_thumb_up
        else R.drawable.ic_pending_reg

    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxSize()
            .verticalScroll(scroll),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(AppGolden),
            modifier = Modifier
                .background(LightGolden.copy(alpha = 0.15f), RoundedCornerShape(10.dp))
                .padding(15.dp)
                .size(70.dp)
        )

        Spacer(Modifier.height(50.dp))

        Text(
            text = bodyText,
            textAlign = TextAlign.Center,
            color = textColor
        )

        Spacer(Modifier.height(50.dp))

        AppButton(
            title = "Done",
            painterResource = R.drawable.ic_arrow_forward,
            onClick = { onButtonClick() }
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SubmissionUIPreview() {
    SubmissionUI(
        bodyText = "You transfer details has been successfully submitted for admin to verified. You will be notified once the transfer is confirmed.\n" +
            "\n" +
            "You can confirmed the status of your transfer in the dashboard",
        onButtonClick = {}
    )
}