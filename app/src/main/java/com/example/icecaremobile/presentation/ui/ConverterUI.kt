package com.example.icecaremobile.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.icecaremobile.R
import com.example.icecaremobile.presentation.ui.component.AppButton
import com.example.icecaremobile.presentation.ui.component.AppTextField
import com.example.icecaremobile.ui.theme.AppGolden
import com.example.icecaremobile.ui.theme.DarkGolden


@Composable
fun ConverterUI(
    modifier: Modifier = Modifier,
    dollarRate: String = "0.0",
    amountEntered: (String) -> Unit,
    onCancelPress: () -> Unit,
    total: Double = 0.0,
    onProceedClick: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .graphicsLayer {
                    shadowElevation = 6.dp.toPx()
                    shape = RoundedCornerShape(20.dp)
                }
                .padding(5.dp)
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                .padding(20.dp)

        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(30.dp)
                    .clickable { onCancelPress() },
                painter = painterResource(id = R.drawable.ic_cancel),
                contentDescription = null,
                tint = Color.Gray,
            )

            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .background(Color.LightGray.copy(0.2f), RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    text = "1   =   ₦${dollarRate}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(50.dp))

                Text(text = "Enter Dollar amount")

                AppTextField(
                    enteredValue = { amountEntered(it) },
                    keyboardType = KeyboardType.Number
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.ic_arrow_down),
            contentDescription = null,
            modifier = Modifier
                .padding(10.dp)
                .background(AppGolden, shape = CircleShape)
                .padding(15.dp)
                .align(Alignment.CenterHorizontally)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .graphicsLayer {
                    shadowElevation = 6.dp.toPx()
                    shape = RoundedCornerShape(20.dp)
                }
                .padding(5.dp)
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Text(text = "Total Naira amount")

                Spacer(Modifier.height(20.dp))

                HorizontalDivider(color = Color.Black)

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "₦${total}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }

        Spacer(Modifier.weight(1f))

        AppButton("Proceed to Payment", { onProceedClick() })

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { onCancelPress() },
            fontSize = 20.sp,
            text = "Back",
            color = DarkGolden,
            textDecoration = TextDecoration.Underline
        )

        Spacer(Modifier.weight(1f))
    }
}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun ConverterUIPreview() {
    ConverterUI(
        amountEntered = {}, onCancelPress = {},
        total = 0.0, onProceedClick = {}
    )
}
