package com.example.icecaremobile.presentation.ui.component

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.icecaremobile.R
import com.example.icecaremobile.ui.theme.DarkGolden
import com.example.icecaremobile.ui.theme.LightGray


@Composable
fun AccountListCard(
    modifier: Modifier = Modifier,
    bankName: String,
    accountName: String,
    accountNumber: String
){
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 10.dp, bottom = 10.dp),
        colors = CardDefaults.cardColors(LightGray)
    ){
        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
        ){
            val verticalGuideline = createGuidelineFromStart(0.3f)
            val (
                bankNameText, bankNameValue,
                accountNameText, accountNameValue,
                accountNumberText, accountNumberValue,
                icon
            ) = createRefs()

            Text(
                text = "Bank Name:",
                color = DarkGolden,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(bankNameText)
                    {
                        start.linkTo(verticalGuideline, margin = 35.dp)
                        start.linkTo(parent.start)
                        top.linkTo(parent.top, margin = 5.dp)
                    }
            )

            Text(
                text = bankName,
                modifier = Modifier
                    .constrainAs(bankNameValue)
                    {
                        top.linkTo(parent.top, margin = 5.dp)
                        start.linkTo(verticalGuideline, margin = 50.dp)
                    },
            )

            Text(
                text = "Account Name:",
                color = DarkGolden,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .constrainAs(accountNameText)
                    {
                        top.linkTo(bankNameText.bottom, margin = 10.dp)
                        start.linkTo(bankNameText.start)
                    },
            )

            Text(
                text = accountName,
                modifier = modifier
                    .constrainAs(accountNameValue)
                    {
                        top.linkTo(bankNameValue.bottom, margin = 10.dp)
                        start.linkTo(bankNameValue.start)
                    },
            )

            Text(
                text = "Account Number:",
                color = DarkGolden,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(accountNumberText)
                    {
                        top.linkTo(accountNameText.bottom, margin = 10.dp)
                        start.linkTo(accountNameText.start)
                    }
            )

            Text(
                text = accountNumber,
                modifier = Modifier
                    .constrainAs(accountNumberValue)
                    {
                        top.linkTo(accountNameValue.bottom, margin = 10.dp)
                        start.linkTo(accountNameValue.start)
                    }
            )

            Icon(
                painter = painterResource(R.drawable.ic_copy),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        clipboardManager.setText(annotatedString = AnnotatedString(accountNumber))
                        Toast
                            .makeText(context, "Account number copied", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .constrainAs(icon)
                    {
                        top.linkTo(accountNameValue.bottom, margin = 10.dp)
                        end.linkTo(parent.end, margin = 5.dp)
                    }
            )

        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AccountListCardPreview(

){
    AccountListCard(
        bankName = "Wema Bank Plc",
        accountName = "Ice Care Nig Ltd",
        accountNumber = "0123456789"
    )
}