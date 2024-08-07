package com.example.icecaremobile.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.icecaremobile.R
import com.example.icecaremobile.presentation.ui.component.AccountListCard
import com.example.icecaremobile.presentation.ui.component.AppButton
import com.example.icecaremobile.presentation.ui.component.ListCard
import com.example.icecaremobile.ui.theme.AppGolden
import com.example.icecaremobile.ui.theme.DarkGolden


@Composable
fun AccountUI(
    modifier: Modifier = Modifier,
    onCheck: () -> Unit,
    onButtonClick: () -> Unit
){
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(scrollState),
    ){
        var checked by remember{ mutableStateOf(false) }

        val items = listOf(
            Triple("United Bank for Africa", "Ice Care Nig Ltd", "0123456789"),
            Triple("Union Bank Plc", "Ice Care Nig Ltd", "1234567890"),
            Triple("Wema Bank Plc", "Ice Care Nig Ltd", "5432109876"),
        )

        LazyColumn{
            items(
                items.count()
            ){ index ->
                AccountListCard(
                    bankName = items[index].first,
                    accountName = items[index].second,
                    accountNumber = items[index].third
                )
            }
        }

        Spacer(Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Checkbox(
                colors = CheckboxDefaults.colors(checkedColor = AppGolden),
                checked = checked,
                onCheckedChange = {
                    onCheck()
                    checked = it
                }
            )

            Text(
                modifier = Modifier.padding(top = 10.dp),
                color = DarkGolden,
                text = "By checking this box, I verify that the transfer has been completed and I have retained a copy of the transfer receipt for my records."
            )
        }

        AppButton(
            title = "Proceed",
            onClick = { onButtonClick() }
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountUIPreview()
{
    AccountUI(onCheck = {}, onButtonClick = {})
}