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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.icecaremobile.R
import com.example.icecaremobile.presentation.ui.component.AppButton
import com.example.icecaremobile.presentation.ui.component.AppTextField
import com.example.icecaremobile.ui.theme.LightGray

@Composable
fun LoginUI(
    modifier: Modifier = Modifier,
    username: (String) -> Unit,
    password: (String) -> Unit,
    btnLogin: () -> Unit
)
{
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(30.dp)
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
            text = "Welcome Back",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )

        Spacer(Modifier.height(1.dp))

        Text(
            text = "sign in to access your account",
            fontWeight = FontWeight.Thin,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(20.dp))

        AppTextField(
            enteredValue = { username(it) },
            label = "Enter your email",
            startIcon = null,
            endIcon = painterResource(R.drawable.ic_email)
        )

        Spacer(Modifier.height(20.dp))

        AppTextField(
            enteredValue = { password(it) },
            label = "Password",
            startIcon = null,
            endIcon = painterResource(R.drawable.ic_password)
        )

        Spacer(Modifier.height(50.dp))

        AppButton(
            title = "Login",
            onClick = { btnLogin() }
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun LoginUIPreview()
{
    LoginUI(username = {}, password = {}, btnLogin = {})
}