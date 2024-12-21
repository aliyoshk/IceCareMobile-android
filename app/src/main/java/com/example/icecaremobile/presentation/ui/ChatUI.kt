package com.example.icecaremobile.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.icecaremobile.R
import com.example.icecaremobile.ui.theme.DarkGolden


@Composable
fun ChatUI(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(50.dp))

        Image(
            modifier = Modifier
                .padding(10.dp)
                .background(Color.LightGray, CircleShape)
                .padding(20.dp)
                .size(150.dp),
            painter = painterResource(R.drawable.logo),
            contentDescription = null
        )

        Spacer(Modifier.height(50.dp))

        Text(
            text = "Not available at the moment",
            color = DarkGolden,
            fontSize = 25.sp
        )
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ChatUIPreview() {
    ChatUI()
}