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
import com.example.icecaremobile.ui.theme.AppGolden

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDropdownMenu(
    bankList: List<String>,
    selectedValue: String,
    label: String,
    onValueChangedEvent: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
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
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            bankList.forEach { option: String ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent("option")
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppDropdownMenuPreview() {
    AppDropdownMenu(
        bankList = listOf("Wema Bank", "GTBank", "First Bank"),
        selectedValue = "",
        label = "Choose bank to transfer",
        onValueChangedEvent = {}
    )
}