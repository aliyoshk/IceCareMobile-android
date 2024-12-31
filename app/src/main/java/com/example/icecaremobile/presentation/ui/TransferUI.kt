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
import com.example.icecaremobile.domain.model.Response.CompanyAccounts
import com.example.icecaremobile.presentation.ui.component.AccountListCard
import com.example.icecaremobile.presentation.ui.component.AppButton
import com.example.icecaremobile.presentation.ui.component.AppDropdownMenu
import com.example.icecaremobile.presentation.ui.component.AppTextField
import com.example.icecaremobile.ui.theme.AppGolden
import com.example.icecaremobile.ui.theme.DarkGolden


@Composable
fun TransferUI(
    modifier: Modifier = Modifier,
    accounts: List<CompanyAccounts>,
    enteredDollar: (String) -> Unit,
    nairaAmount: (String) -> Unit,
    purpose: (String) -> Unit,
    selectedBank: (CompanyAccounts) -> Unit,
    isTermsChecked: Boolean,
    onTermsCheckedChange: (Boolean) -> Unit,
    onButtonClick: () -> Unit = {},
    isError: () -> Map<String, String>
) {
    val scrollState = rememberScrollState()
    val errors = isError()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(scrollState)
            .padding(bottom = 50.dp),
    ) {
        var selectedBankDetails by remember { mutableStateOf<CompanyAccounts?>(null) }

        Text("Amount transferred (Naira)")
        AppTextField(
            enteredValue = nairaAmount,
            label = "",
            startIcon = null,
            endIcon = null,
            keyboardType = KeyboardType.Number,
            isError = errors.containsKey("nairaAmount"),
            errorMessage = errors["nairaAmount"]
        )

        Spacer(Modifier.height(20.dp))

        Text("Dollar Amount ($)")
        AppTextField(
            enteredValue = enteredDollar,
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
            endIcon = null,
            isError = errors.containsKey("description"),
            errorMessage = errors["description"]
        )

        Spacer(Modifier.height(20.dp))

        Text("Choose bank to transfer")
        AppDropdownMenu(
            accounts = accounts,
            label = "",
            onValueChangedEvent = {
                selectedBank(it)
                selectedBankDetails = it
            },
            isError = errors.containsKey("bankSelected"),
            errorMessage = errors["bankSelected"]
        )

        selectedBankDetails?.let {

            Spacer(Modifier.height(20.dp))

            Text("Account Details")
            AccountListCard(
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp),
                bankName = selectedBankDetails!!.bankName,
                accountName = selectedBankDetails!!.accountName,
                accountNumber = selectedBankDetails!!.accountNumber
            )

            Row(
                modifier = Modifier
                    .wrapContentWidth()
            ) {
                Checkbox(
                    colors = CheckboxDefaults.colors(checkedColor = AppGolden),
                    checked = isTermsChecked,
                    onCheckedChange = onTermsCheckedChange
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
        accounts = listOf(
            CompanyAccounts("Wema Bank", "Ice Care Nig Ltd", "1234567890"),
            CompanyAccounts("GTBank", "Ice Care Nig Ltd", "0987654321"),
            CompanyAccounts("First Bank", "Ice Care Nig Ltd", "1122334455")
        ),
        isTermsChecked = false,
        onTermsCheckedChange = {},
        enteredDollar = { },
        nairaAmount = {},
        purpose = {},
        selectedBank = {  },
        isError = { mapOf() },
    )
}