package com.example.icecaremobile.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.icecaremobile.R
import com.example.icecaremobile.presentation.ui.component.AppButton
import com.example.icecaremobile.presentation.ui.component.AppTextField
import com.example.icecaremobile.ui.theme.LightGray

@Composable
fun RegistrationUI(
    modifier: Modifier = Modifier,
    fullName: (String) -> Unit,
    email: (String) -> Unit,
    phoneNumber: (String) -> Unit,
    password: (String) -> Unit,
    confirmPassword: (String) -> Unit,
    btnSignUp: () -> Unit
)
{
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 30.dp, end = 30.dp, bottom = 30.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    )
    {
        Image(
            painter = painterResource(R.drawable.circle_logo),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = Modifier.size(200.dp)
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Get Started",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )

        Spacer(Modifier.height(1.dp))

        Text(
            text = "by creating an account.",
            fontWeight = FontWeight.Thin,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(20.dp))

        AppTextField(
            enteredValue = { fullName(it) },
            label = "Full name",
            startIcon = null,
            endIcon = painterResource(R.drawable.ic_person)
        )

        Spacer(Modifier.height(20.dp))

        AppTextField(
            enteredValue = { email(it) },
            label = "Valid email",
            startIcon = null,
            endIcon = painterResource(R.drawable.ic_email)
        )

        Spacer(Modifier.height(20.dp))

        AppTextField(
            enteredValue = { phoneNumber(it) },
            label = "Phone number",
            startIcon = null,
            endIcon = painterResource(R.drawable.ic_phone),
            keyboardType = KeyboardType.Number
        )

        Spacer(Modifier.height(20.dp))

        AppTextField(
            enteredValue = { password(it) },
            label = "Password",
            startIcon = null,
            endIcon = painterResource(R.drawable.ic_password),
            keyboardType = KeyboardType.Password
        )

        Spacer(Modifier.height(20.dp))

        AppTextField(
            enteredValue = { confirmPassword(it) },
            label = "Confirm password",
            startIcon = null,
            endIcon = painterResource(R.drawable.ic_password),
            keyboardType = KeyboardType.Password
        )

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(2.dp)
        )
        {
            Checkbox(
                checked = false,
                onCheckedChange = {},
                colors = CheckboxDefaults.colors(LightGray)
            )

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "By checking the box you agree to our Terms and Conditions.",
                fontWeight = FontWeight.Thin,
                fontSize = 14.sp,
            )
        }

        AppButton("Sign Up", { btnSignUp() })
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun RegistrationUIPreview()
{
    RegistrationUI(modifier = Modifier, {}, {}, {}, {}, {}, {})
}
