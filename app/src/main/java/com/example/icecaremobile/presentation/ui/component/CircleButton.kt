package com.example.icecaremobile.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.icecaremobile.R
import com.example.icecaremobile.ui.theme.AppGolden
import com.example.icecaremobile.ui.theme.IceCareMobileTheme

@Composable
fun CircleButton(onClick: () -> Unit)
{
    Image(
        painter = painterResource(id = R.drawable.ic_arrow_forward),
        contentDescription = null,
        modifier = Modifier
            .padding(10.dp)
            .background(AppGolden, shape = CircleShape)
            .padding(15.dp)
            .clickable { onClick() }
    )
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CircleButtonPreview()
{
    CircleButton({})
}