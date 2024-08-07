package com.example.icecaremobile.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.icecaremobile.R
import com.example.icecaremobile.presentation.ui.component.ListCard

@Composable
fun SendMoneyUI(
    modifier: Modifier = Modifier,
    optionTitle: (String) -> Unit,
    onClick: () -> Unit
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
    ){
        val items = listOf(
            Triple(R.drawable.ic_hands, "To Single Account", "Transfer to one company account"),
            Triple(R.drawable.ic_wallet, "To Multiple Accounts", "Transfer to multiple company account"),
            Triple(R.drawable.ic_world, "From Account Balance", "Nudge admin to Remit from your balance"),
            Triple(R.drawable.ic_world, "To Third Party Account", "Nudge to Withdraw balance to third party")
        )

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