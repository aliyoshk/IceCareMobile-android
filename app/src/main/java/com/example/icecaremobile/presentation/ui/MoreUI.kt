package com.example.icecaremobile.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.icecaremobile.R
import com.example.icecaremobile.presentation.ui.component.ListCard


@Composable
fun MoreUI(
    modifier: Modifier = Modifier,
    items: List<Triple<Int, String, String>> = emptyList(),
    optionTitle: (String) -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(20.dp))

        LazyColumn {
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
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MoreUIPreview() {
    val items = listOf(
        Triple(R.drawable.ic_phone, "Call Us", "Contact call center"),
        Triple(R.drawable.ic_chat, "Chat With Us", "Send an in app message"),
        Triple(R.drawable.ic_question, "FAQS", "Frequently asked questions")
    )
    MoreUI(items = items, optionTitle = {}, onClick = {})
}