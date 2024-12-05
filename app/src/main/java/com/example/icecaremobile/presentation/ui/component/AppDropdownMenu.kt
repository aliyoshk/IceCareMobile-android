package com.example.icecaremobile.presentation.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.icecaremobile.domain.model.Response.CompanyAccounts
import com.example.icecaremobile.ui.theme.AppGolden

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDropdownMenu(
    accounts: List<CompanyAccounts>,
    label: String,
    onValueChangedEvent: (CompanyAccounts) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var currentSelectedValue by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        OutlinedTextField(
            readOnly = true,
            value = currentSelectedValue,
            onValueChange = {},
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                Color.Black,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = AppGolden
            ),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            isError = isError,
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            accounts.forEach { option: CompanyAccounts ->
                DropdownMenuItem(
                    text = { Text(text = option.bankName) },
                    onClick = {
                        currentSelectedValue = option.bankName
                        onValueChangedEvent(option)
                        expanded = false
                    }
                )
            }
        }
    }

    if (isError && !errorMessage.isNullOrEmpty()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = errorMessage,
            color = Color.Red,
            fontSize = 12.sp,
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppDropdownMenuPreview() {
    AppDropdownMenu(
        accounts = listOf(
            CompanyAccounts("Wema Bank", "Ice Care Nig Ltd", "1234567890"),
            CompanyAccounts("GTBank", "Ice Care Nig Ltd", "0987654321"),
            CompanyAccounts("First Bank", "Ice Care Nig Ltd", "1122334455")
        ),
        label = "Choose bank to transfer",
        onValueChangedEvent = {}
    )
}