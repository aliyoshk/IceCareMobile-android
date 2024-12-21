package com.example.icecaremobile.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.icecaremobile.ui.theme.DarkGolden

@Composable
fun AppLoader() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(60.dp),
            color = DarkGolden,
        )
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun AppLoaderPreview() {
    AppLoader()
}