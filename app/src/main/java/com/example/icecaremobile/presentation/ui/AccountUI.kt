package com.example.icecaremobile.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.icecaremobile.domain.model.Response.CompanyAccounts
import com.example.icecaremobile.presentation.ui.component.AccountListCard
import com.example.icecaremobile.presentation.ui.component.AppButton
import com.example.icecaremobile.ui.theme.AppGolden
import com.example.icecaremobile.ui.theme.DarkGolden


@Composable
fun AccountUI(
    modifier: Modifier = Modifier,
    onCheck: () -> Unit,
    banks: List<CompanyAccounts>,
    onButtonClick: () -> Unit
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp)
    ){
        var checked by remember{ mutableStateOf(false) }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            items(
                banks.count()
            ){ index ->
                AccountListCard(
                    bankName = banks[index].bankName,
                    accountName = banks[index].accountName,
                    accountNumber = banks[index].accountNumber
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 16.dp)
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
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 10.dp),
                color = DarkGolden,
                text = "By checking this box, I verify that the transfer has been completed and I have retained a copy of my transfer receipt for my records."
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

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
    val items = listOf(
        CompanyAccounts("United Bank for Africa", "Ice Care Nig Ltd", "0123456789"),
        CompanyAccounts("Union Bank Plc", "Ice Care Nig Ltd", "1234567890"),
        CompanyAccounts("Wema Bank Plc", "Ice Care Nig Ltd", "5432109876"),
        CompanyAccounts("United Bank for Africa", "Ice Care Nig Ltd", "0123456789"),
        CompanyAccounts("Wema Bank Plc", "Ice Care Nig Ltd", "5432109876"),
        CompanyAccounts("United Bank for Africa", "Ice Care Nig Ltd", "0123456789"),
        CompanyAccounts("Wema Bank Plc", "Ice Care Nig Ltd", "5432109876"),
        CompanyAccounts("United Bank for Africa", "Ice Care Nig Ltd", "0123456789"),
    )
    AccountUI(onCheck = {}, banks = items, onButtonClick = {})
}