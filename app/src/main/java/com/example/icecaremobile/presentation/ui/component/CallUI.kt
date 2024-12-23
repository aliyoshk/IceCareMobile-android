package com.example.icecaremobile.presentation.ui.component

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.icecaremobile.R
import com.example.icecaremobile.domain.model.Response.CompanyPhones
import com.example.icecaremobile.ui.theme.DarkGolden

@Composable
fun CallUI(
    modifier: Modifier = Modifier,
    numbers: List<CompanyPhones> = emptyList()
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .padding(10.dp)
                .background(Color.LightGray, CircleShape)
                .padding(20.dp)
                .size(100.dp),
            painter = painterResource(R.drawable.logo),
            contentDescription = null
        )

        Spacer(Modifier.height(30.dp))

        Text(
            text = "Hi, let's help you today",
            color = DarkGolden,
            fontSize = 22.sp
        )

        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = "Phone lines are available between 8:00am and 5:00pm on weekdays",
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(30.dp))

        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = "Tap on any number to call",
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(10.dp))

        LazyColumn {
            items(numbers.count()) { index ->
                CallItem(number = numbers[index].phoneNumber, context = context)
            }
        }
    }
}

@Composable
fun CallItem(
    number: String,
    context: Context
) {
    Row(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "⦿   $number",
            fontSize = 18.sp,
            color = DarkGolden,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.width(20.dp))

        IconButton(onClick = { makeCall(number, context) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_call),
                contentDescription = "Call",
                modifier = Modifier.size(20.dp),
                tint = Color.Gray
            )
        }
    }
}

fun makeCall(number: String, context: Context) {
    val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
    context.startActivity(dialIntent)
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CallUIPreview() {
    val numbers = listOf(
        CompanyPhones("08102884291"),
        CompanyPhones("08182758531")
    )
    CallUI(numbers = numbers)
}