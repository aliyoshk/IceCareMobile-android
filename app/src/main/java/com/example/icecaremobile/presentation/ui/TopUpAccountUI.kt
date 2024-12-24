package com.example.icecaremobile.presentation.ui

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.icecaremobile.domain.model.Response.CompanyAccounts
import com.example.icecaremobile.presentation.ui.component.AppButton
import com.example.icecaremobile.presentation.ui.component.AppTextField
import com.example.icecaremobile.ui.theme.DarkGolden


@Composable
fun TopUpAccountUI(
    modifier: Modifier = Modifier,
    banks: List<CompanyAccounts>,
    purpose: (String) -> Unit,
    selectedBanks: (List<CompanyAccounts>) -> Unit,
    enteredAmounts: (Map<String, String>) -> Unit,
    receiptUploaded: (List<Uri>) -> Unit,
    onSubmitClick: () -> Unit,
    isError: () -> Map<String, String>
) {
    val scrollState = rememberScrollState()
    val errors = isError()
    val radioOptions = listOf("Naira", "Dollar")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(scrollState),
    ) {
        Text("Choose Currency")
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .selectableGroup()
        ) {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .selectableGroup()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) },
                            role = Role.RadioButton
                        )
                        .padding(end = 30.dp)
                        .border(2.dp,
                            if(text == selectedOption) DarkGolden else Color.LightGray,
                            RoundedCornerShape(10.dp)
                        )
                        .padding(10.dp)
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = null, // null recommended for accessibility with screen readers
                        colors = RadioButtonColors(
                            selectedColor = DarkGolden,
                            unselectedColor = Color.Black,
                            disabledSelectedColor = Color.Gray,
                            disabledUnselectedColor = Color.Gray
                        )
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        //Reusing Multiple transfer function
        PopulateBanks(
            bankList = banks,
            onBankSelectionChanged = selectedBanks,
            onAmountEntered = enteredAmounts,
            errors = errors,
            selectedOption
        )

        Spacer(Modifier.height(5.dp))

        Text("Purpose of payment")
        AppTextField(
            enteredValue = purpose,
            label = "",
            startIcon = null,
            endIcon = null,
            isError = errors.containsKey("purpose"),
            errorMessage = errors["purpose"]
        )

        Spacer(Modifier.height(20.dp))
        //Reusing Multiple transfer function
        ReceiptUploadField(
            onReceiptsChanged = receiptUploaded, errors = errors
        )

        Spacer(Modifier.height(50.dp))

        AppButton(
            title = "Submit",
            onClick = onSubmitClick
        )

    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TopUpAccountUIPreview() {
    val banks = listOf(
        CompanyAccounts("United Bank for Africa", "Ice Care Nig Ltd", "0123456789"),
        CompanyAccounts("Union Bank Plc", "Ice Care Nig Ltd", "1234567890"),
        CompanyAccounts("Wema Bank Plc", "Ice Care Nig Ltd", "5432109876"),
    )
    TopUpAccountUI(
        banks = banks,
        purpose = {},
        selectedBanks = {},
        enteredAmounts = {},
        receiptUploaded = {},
        onSubmitClick = {},
        isError = { mapOf() }
    )
}