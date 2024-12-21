package com.example.icecaremobile.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.icecaremobile.R
import com.example.icecaremobile.presentation.ui.component.AcceptDialog
import com.example.icecaremobile.presentation.ui.component.AppButton
import com.example.icecaremobile.ui.theme.DarkGolden
import com.example.icecaremobile.ui.theme.LightGolden
import com.example.icecaremobile.ui.theme.LightGray


@Composable
fun StatusUI(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    status: String
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (status.lowercase().contains("pending")) {

            Spacer(Modifier.weight(1f))

            Image(
                painter = painterResource(R.drawable.ic_pending),
                contentDescription = null,
                modifier = Modifier
                    .background(LightGolden.copy(alpha = 0.2f), RoundedCornerShape(5.dp))
                    .padding(15.dp)
            )

            Spacer(Modifier.height(30.dp))

            Text(
                text = "Thank You!",
                color = DarkGolden
            )

            Spacer(Modifier.height(5.dp))

            Text("Your transaction is being processed.")

            Spacer(Modifier.height(30.dp))

            Text(
                textAlign = TextAlign.Center,
                letterSpacing = 2.sp,
                lineHeight = 18.sp,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .background(LightGray.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                    .padding(top = 20.dp, bottom = 20.dp, start = 10.dp, end = 10.dp),
                color = DarkGolden,
                text = status,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(60.dp))

            AppButton("Go to Dashboard", { onButtonClick() })

            Spacer(Modifier.weight(1f))
        }  else {
            AcceptDialog(
                title = "Notification",
                message = status,
                buttonText = "Ok",
                onButtonClick = { onButtonClick() },
                onDismissRequest = { }
            )
        }
    }
}

@Composable
fun PendingUI() {
    Image(
        painter = painterResource(R.drawable.ic_pending),
        contentDescription = null,
        modifier = Modifier
            .background(LightGolden.copy(alpha = 0.3f), RoundedCornerShape(5.dp))
            .padding(15.dp)
    )

    Text(
        text = "Thank You!",
        color = DarkGolden
    )

    Text("Your transaction is being processed.")

    Text(
        textAlign = TextAlign.Center,
        letterSpacing = 2.sp,
        lineHeight = 18.sp,
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .background(LightGray.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
            .padding(top = 20.dp, bottom = 20.dp, start = 10.dp, end = 10.dp),
        color = DarkGolden,
        text = "Once your transfer is confirmed, you will be redirected to view and download transaction(s) related documents.",
        fontSize = 14.sp
    )

    AppButton("Go to Dashboard", {})
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun StatusUIPreview() {
    StatusUI(onButtonClick = {}, status = "pending")
}