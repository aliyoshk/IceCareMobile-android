package com.example.icecaremobile.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.icecaremobile.presentation.ui.component.AccountListCard
import com.example.icecaremobile.presentation.ui.component.AppButton
import com.example.icecaremobile.presentation.ui.component.AppDropdownMenu
import com.example.icecaremobile.presentation.ui.component.AppTextField
import com.example.icecaremobile.ui.theme.AppGolden
import com.example.icecaremobile.ui.theme.DarkGolden


@Composable
fun TransferUI(
    modifier: Modifier = Modifier,
    banks: List<String>,
    dollarAmount: (String) -> Unit,
    nairaAmount: (String) -> Unit,
    purpose: (String) -> Unit,
    selectedBank: (String) -> Unit,
    onChecked: () -> Unit = {},
    bankDetails: Triple<String, String, String>,
    onButtonClick: () -> Unit = {}
) {

    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(scrollState),
    ) {
        var bankState by remember { mutableStateOf(false) }
        var checked by remember{ mutableStateOf(false) }

        Text("Dollar Amount ($)")

        AppTextField(
            enteredValue = dollarAmount,
            label = "",
            startIcon = null,
            endIcon = null,
            keyboardType = KeyboardType.Number
        )

        Spacer(Modifier.height(20.dp))

        Text("Amount transferred (Naira)")

        AppTextField(
            enteredValue = nairaAmount,
            label = "",
            startIcon = null,
            endIcon = null,
            keyboardType = KeyboardType.Number
        )

        Spacer(Modifier.height(20.dp))

        Text("Purpose of payment")

        AppTextField(
            enteredValue = purpose,
            label = "",
            startIcon = null,
            endIcon = null
        )

        Spacer(Modifier.height(20.dp))

        Text("Choose bank to transfer")

        AppDropdownMenu(
            selectedValue = "",
            bankList = banks,
            label = "",
            onValueChangedEvent = {
                selectedBank(it)
                if (it.isNotEmpty())
                    bankState = true
                else
                    bankState = false
            }
        )

        if (!bankState)
        {
            Spacer(Modifier.height(20.dp))

            Text("Choose bank to transfer")

            AccountListCard(
                modifier = Modifier
                    .padding(top= 15.dp, bottom = 15.dp),
                bankName = bankDetails.first,
                accountName = bankDetails.second,
                accountNumber = bankDetails.third
            )

            Row(
                modifier = Modifier
                    .wrapContentWidth()
            ) {
                Checkbox(
                    colors = CheckboxDefaults.colors(checkedColor = AppGolden),
                    checked = checked,
                    onCheckedChange = {
                        onChecked()
                        checked = it
                    }
                )

                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    color = DarkGolden,
                    text = "By checking this box, I verify that the transfer has been completed and I have retained a copy of the transfer receipt for my records."
                )
            }
        }

        Spacer(Modifier.weight(1f))

        AppButton(
            title = "Proceed",
            onClick = { onButtonClick() }
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TransferUIPreview() {
    TransferUI(
        banks = listOf("Wema Bank", "GTBank", "First Bank"),
        dollarAmount = {},
        nairaAmount = {},
        purpose = {},
        bankDetails = Triple("United Bank for Africa", "Ice Care Nig Ltd", "0123456789"),
        selectedBank = {}
    )
}