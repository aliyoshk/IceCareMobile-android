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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.icecaremobile.presentation.ui.component.AppButton
import com.example.icecaremobile.presentation.ui.component.AppTextField
import com.example.icecaremobile.ui.theme.AppGolden
import com.example.icecaremobile.ui.theme.DarkGolden


@Composable
fun AccountBalanceTransferUI(
    modifier: Modifier = Modifier,
    nairaAmountEntered: (String) -> Unit,
    dollarEquivalence: String,
    description: (String) -> Unit,
    boxCheck: () -> Unit,
    buttonClicked: () -> Unit
) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        var checked by remember{ mutableStateOf(false) }

        Text("Enter amount (Naira)")

        AppTextField(
            enteredValue = { nairaAmountEntered(it) },
            label = "",
            startIcon = null,
            endIcon  = null,
            keyboardType = KeyboardType.Number
        )

        Spacer(Modifier.height(20.dp))

        Text("Amount in Dollar($)")

        AppTextField(
            enteredValue = { },
            label = dollarEquivalence,
            startIcon = null,
            endIcon  = null,
            keyboardType = KeyboardType.Number,
            enableField = false
        )

        Spacer(Modifier.height(30.dp))

        Text("Description")

        AppTextField(
            enteredValue = { description(it) },
            label = "",
            startIcon = null,
            endIcon  = null,
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
                    boxCheck()
                    checked = it
                }
            )

            Text(
                modifier = Modifier.padding(top = 10.dp),
                color = DarkGolden,
                text = "I authorise Ice Care Nig Ltd to make payments from my account balance"
            )
        }

        AppButton(
            title = "Submit",
            onClick = {buttonClicked()}
        )
    }
}



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AccountBalanceTransferUIPreview()
{
    AccountBalanceTransferUI(
        nairaAmountEntered = {},
        description = {},
        dollarEquivalence = "4000",
        boxCheck = {},
        buttonClicked = {}
    )
}