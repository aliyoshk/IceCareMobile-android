package com.example.icecaremobile.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ConverterUI(
    modifier: Modifier = Modifier,
    //dollarRate: Double
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Hi",
            modifier = Modifier
                .verticalScroll(scrollState)
        )
    }

//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .verticalScroll(scrollState),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(scrollState)
//                .background(
//                    color = Color.Red,
//                    shape = RoundedCornerShape(10.dp)
//                )
//        ) {
//
//            Text(
//                text = "Hi",
//                fontWeight = FontWeight.Bold,
//                fontSize = 22.sp,
//                color = Color.Red
//            )
//
//            Text(
//                text = "Hi",
//                modifier = Modifier
//                    .background(Color.Blue, CircleShape),
//                fontWeight = FontWeight.Bold,
//                fontSize = 22.sp,
//
//            )
//
//            AppTextField(
//                enteredValue = {},
//                label = "Enter Amount",
//            )
//        }
//
//        Box(
//            modifier = Modifier
//                //.size(40.dp)
//                .padding(10.dp)
//                .background(AppGolden, shape = CircleShape)
//                .padding(15.dp)
//                .background(AppGolden, CircleShape),
//            contentAlignment = Alignment.Center
//        ) {
//            IconButton(onClick = { }) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_arrow_up),
//                    contentDescription = "Arrow Up",
//                    tint = Color.White
//                )
//                Icon(
//                    painter = painterResource(R.drawable.ic_arrow_down),
//                    contentDescription = "Arrow Down",
//                    tint = Color.White
//                )
//            }
//        }
//    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ConverterUIPreview() {
    ConverterUI()
}