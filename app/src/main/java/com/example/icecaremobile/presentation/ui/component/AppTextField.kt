package com.example.icecaremobile.presentation.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.icecaremobile.R
import com.example.icecaremobile.ui.theme.AppGolden

@Composable
fun AppTextField(
    enteredValue: (String) -> Unit,
    label: String,
    startIcon: Painter? = null,
    endIcon: Painter? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    enableField: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = { Text(label, color = Color.Black) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        value = text,
        onValueChange =
        {
            text = it
            enteredValue(text)
        },
        enabled = enableField,
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            Color.Black,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = AppGolden,
            disabledIndicatorColor = Color.Transparent
        ),
        trailingIcon =
        {
            if (endIcon != null)
            {
                Icon(
                    painter = endIcon,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        isError = isError
    )
    if (isError && !errorMessage.isNullOrEmpty()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = errorMessage,
            color = Color.Red,
            fontSize = 12.sp,
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun AppTextFieldPreview()
{
    AppTextField(
        enteredValue = {},
        label = "Enter City",
        startIcon = null,
        endIcon = painterResource(R.drawable.ic_arrow_forward)
    )
}