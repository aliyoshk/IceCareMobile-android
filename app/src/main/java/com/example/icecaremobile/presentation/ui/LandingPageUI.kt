package com.example.icecaremobile.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.icecaremobile.R
import com.example.icecaremobile.presentation.ui.component.CircleButton
import com.example.icecaremobile.ui.theme.AppGolden
import kotlinx.coroutines.delay

@Composable
fun LandingPageUI(btnClick: () -> Unit)
{
    Image(
        painter = painterResource(id = R.drawable.carousel_bg),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )

    val items = listOf(
        Triple(R.drawable.ic_hands, "Explore endless possibilities", "To your desire"),
        Triple(R.drawable.ic_wallet, "Secure and Reliable", "Made for your business"),
        Triple(R.drawable.ic_world, "Global payments made easy", "Streamlined for your ease")
    )

    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(10 * 1000)
            currentIndex = (currentIndex + 1) % items.size
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = items[currentIndex].first),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(40.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = items[currentIndex].second,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                lineHeight = 35.sp,
                modifier = Modifier
                    .padding(16.dp, end = 90.dp)
            )

            Text(
                text = items[currentIndex].third,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                color = Color.Gray,
                modifier = Modifier.padding(16.dp, top = 10.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(end = 16.dp)
            ){
                IndicatorDots(size = items.size, currentIndex = currentIndex)

                Spacer(Modifier.weight(1f))

                CircleButton( btnClick )
            }
        }
    }
}

@Composable
fun IndicatorDots(size: Int, currentIndex: Int) {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until size) {
            val color = if (i == currentIndex) AppGolden else Color.LightGray
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(10.dp)
                    .padding(2.dp)
                    .background(color, shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth()
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun LandingPageUIPreview() {
    LandingPageUI({})
}