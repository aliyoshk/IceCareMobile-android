package com.example.icecaremobile.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.icecaremobile.R


@Composable
fun ListCard(
    icon: Painter,
    title: String,
    subtitle: String,
    onclick: () -> Unit
){
    Card(
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke( 4.dp, Color.LightGray.copy(alpha = 0.3f)),
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onclick() }
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(45.dp)
            )

            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
            ){
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = subtitle,
                    maxLines = 1
                )
            }

            Spacer(Modifier.weight(1f))

            Image(
                painter = painterResource(R.drawable.ic_arrow_forward),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Black),
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListCardPPreview() {
    ListCard(
        icon = painterResource(R.drawable.ic_converter),
        title = "To Single Account",
        subtitle = "Transfer to one company account",
        onclick = {}
    )
}