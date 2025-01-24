package com.example.icecaremobile.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.icecaremobile.R
import com.example.icecaremobile.ui.theme.AppGolden

@Composable
fun AppButton(title: String, onClick: () -> Unit, painterResource: Int = 0
) {
    Button(
        colors = ButtonDefaults.buttonColors(AppGolden),
        shape = RoundedCornerShape(10.dp),
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(20.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (painterResource != 0)
        {
            Image(
                painterResource(painterResource),
                contentDescription = null,
                Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun AppButtonPreview() {
    AppButton("Login", {}, painterResource = R.drawable.ic_arrow_forward)
}