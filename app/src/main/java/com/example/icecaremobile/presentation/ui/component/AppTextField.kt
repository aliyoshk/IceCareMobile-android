package com.example.icecaremobile.presentation.ui.component

import androidx.annotation.ColorRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.icecaremobile.R
import com.example.icecaremobile.ui.theme.AppGolden
import com.example.icecaremobile.ui.theme.LightGray

@Composable
fun AppTextField(
    enteredValue: (String) -> Unit,
    label: String,
    startIcon: Painter?,
    endIcon: Painter?,
    keyboardType: KeyboardType = KeyboardType.Text
)
{
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        value = text,
        onValueChange =
        {
            text = it
            enteredValue(text)
        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            Color.Black,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = AppGolden
        ),
        modifier = Modifier .fillMaxWidth(),

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
    )
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