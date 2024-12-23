package com.example.icecaremobile.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.icecaremobile.presentation.ui.component.ListCard

@Composable
fun SendMoneyUI(
    modifier: Modifier = Modifier,
    items: List<Triple<Int, String, String>> = emptyList(),
    optionTitle: (String) -> Unit,
    onClick: () -> Unit
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
    ){
        LazyColumn{
            items(
                items.count()
            ){ index ->
                ListCard(
                    icon = painterResource(id = items[index].first),
                    title = items[index].second,
                    subtitle = items[index].third,
                    onclick = {
                        onClick()
                        optionTitle(items[index].second)
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SendMoneyUIPreview()
{
    SendMoneyUI(
        optionTitle = {},
        onClick = {}
    )
}