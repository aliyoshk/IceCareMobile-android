package com.example.icecaremobile.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.icecaremobile.presentation.ui.component.AppButton
import com.example.icecaremobile.presentation.ui.component.AppTextField
import com.example.icecaremobile.ui.theme.AppGolden
import com.example.icecaremobile.ui.theme.DarkGolden


@Composable
fun ThirdPartyTransferUI(
    modifier: Modifier = Modifier,
    amount: (String) -> Unit,
    bankName: (String) -> Unit,
    accountNumber: (String) -> Unit,
    accountName: (String) -> Unit,
    description: (String) -> Unit,
    boxChecked: () -> Unit,
    submitClicked: () -> Unit
) {
    var checked by remember{ mutableStateOf(false) }
    val scroll = rememberScrollState()

    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxSize()
            .verticalScroll(scroll),
    ) {

        Text("Amount to transfer (Naira)")

        AppTextField(
            enteredValue = { amount(it) },
            label = "",
            keyboardType = KeyboardType.Number
        )

        Spacer(Modifier.height(20.dp))

        Text("Bank Name")

        AppTextField(
            enteredValue = { bankName(it) },
            label = ""
        )

        Spacer(Modifier.height(20.dp))

        Text("Account Number")

        AppTextField(
            enteredValue = { accountNumber(it) },
            label = "",
            keyboardType = KeyboardType.Number
        )

        Spacer(Modifier.height(20.dp))

        Text("Account Name")

        AppTextField(
            enteredValue = { accountName(it) },
            label = ""
        )

        Spacer(Modifier.height(20.dp))

        Text("Description")

        AppTextField(
            enteredValue = { description(it) },
            label = ""
        )

        Spacer(Modifier.weight(1f))

        Row(
            modifier = Modifier
                .wrapContentWidth()
        ) {
            Checkbox(
                colors = CheckboxDefaults.colors(checkedColor = AppGolden),
                checked = checked,
                onCheckedChange = {
                    boxChecked()
                    checked = it
                }
            )

            Text(
                modifier = Modifier.padding(top = 10.dp),
                color = DarkGolden,
                text = "I authorize Ice Care Nig Ltd to transfer the specified amount from my account balance to the designated bank account. I confirm that the bank account details are accurate and correct, and I understand that this transfer is final and cannot be reversed. By checking this box, I consent to this transaction and release Ice Care Nig Ltd from any liability for errors or discrepancies"
            )
        }

        AppButton(
            title = "Submit",
            onClick = {submitClicked()}
        )
    }
}



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ThirdPartyTransferUIPreview() {
    ThirdPartyTransferUI(modifier = Modifier, {}, {}, {}, {}, {}, {}, {})
}