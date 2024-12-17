package com.example.icecaremobile.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.icecaremobile.R
import com.example.icecaremobile.presentation.ui.component.AppButton
import com.example.icecaremobile.ui.theme.DarkGolden
import com.example.icecaremobile.ui.theme.LightGolden


@Composable
fun ReceiptUI(
    modifier: Modifier = Modifier,
    goToDashboard: () -> Unit,
    downloadReceipt: () -> Unit
) {

    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize()
            .padding(bottom = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .padding(start = 10.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Transfer Receipt",
                fontSize = 20.sp,
                color = DarkGolden
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp)
                .background(LightGolden.copy(0.3f))
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Transaction Date:",
                fontSize = 16.sp,
                color = DarkGolden
            )

            Text(
                text = "03-Jan-2022",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(20.dp))

        val items = listOf(
            Triple("Third Party Transfer", "UBA | 0123456789", "-78000"),
            Triple("Fund Account", "0221345675", "+50000"),
            Triple("Fund Transfer", "01834634643 - Ice Care Nig ltd", "+50000"),
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(items.count()
            ) { index ->
                ReceiptListItem(
                    "NGN 50,000.00",
                    "",
                    "",
                    "",
                    ""
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        AppButton("Go to Dashboard", { goToDashboard() })

        Text(
            modifier = Modifier.clickable { downloadReceipt() },
            text = "Download",
            fontSize = 16.sp,
            color = DarkGolden,
            textDecoration = TextDecoration.Underline,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(20.dp))
    }
}


@Composable
fun ReceiptListItem(
    amount: String, amountInWords: String, acctNumber: String,
    acctName: String, bankName: String
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(5.dp)
    ) {
        val (
            detailsText, divider, amountRef, amountTextRef, amountInWordsTextRef,
            amountInWordsRef, acctNumberTextRef, acctNumberRef, acctNameTextRef,
            acctNameRef, bankNameTextRef, bankNameRef
        ) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(detailsText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            text = "Transaction Details",
            fontSize = 16.sp,
            color = DarkGolden
        )

        HorizontalDivider(
            modifier = Modifier
                .constrainAs(divider) {
                    top.linkTo(detailsText.bottom, 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            color = Color.Black
        )

        Text(
            modifier = Modifier
                .constrainAs(amountTextRef) {
                    top.linkTo(divider.bottom, 20.dp)
                    start.linkTo(parent.start)
                },
            text = "Transaction Amount:",
            color = DarkGolden
        )

        Text(
            modifier = Modifier
                .constrainAs(amountRef) {
                    top.linkTo(divider.bottom, 20.dp)
                    start.linkTo(amountTextRef.end, 50.dp)
                    end.linkTo(parent.end)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                },
            text = "NGN 50,000.00"
        )

        Text(
            modifier = Modifier
                .constrainAs(amountInWordsTextRef) {
                    top.linkTo(amountTextRef.bottom, 20.dp)
                    start.linkTo(parent.start)
                },
            text = "Amount in words:",
            color = DarkGolden
        )

        Text(
            modifier = Modifier
                .constrainAs(amountInWordsRef) {
                    top.linkTo(amountRef.bottom, 20.dp)
                    start.linkTo(amountRef.start)
                    end.linkTo(parent.end)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                },
            text = "Fifty Thousand Naira Only"
        )

        Text(
            modifier = Modifier
                .constrainAs(acctNumberTextRef) {
                    top.linkTo(amountInWordsTextRef.bottom, 20.dp)
                    start.linkTo(parent.start)
                },
            text = "Account Number:",
            color = DarkGolden
        )

        Text(
            modifier = Modifier
                .constrainAs(acctNumberRef) {
                    top.linkTo(amountInWordsRef.bottom, 20.dp)
                    start.linkTo(amountInWordsRef.start)
                    end.linkTo(parent.end)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                },
            text = "21342213211"
        )

        Text(
            modifier = Modifier
                .constrainAs(acctNameTextRef) {
                    top.linkTo(acctNumberTextRef.bottom, 20.dp)
                    start.linkTo(parent.start)
                },
            text = "Account Name:",
            color = DarkGolden
        )

        Text(
            modifier = Modifier
                .constrainAs(acctNameRef) {
                    top.linkTo(acctNumberRef.bottom, 20.dp)
                    start.linkTo(acctNumberRef.start)
                    end.linkTo(parent.end)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                },
            text = "Ice Care Nig Ltd"
        )

        Text(
            modifier = Modifier
                .constrainAs(bankNameTextRef) {
                    top.linkTo(acctNameTextRef.bottom, 20.dp)
                    start.linkTo(parent.start)
                },
            text = "Bank:",
            color = DarkGolden
        )

        Text(
            modifier = Modifier
                .constrainAs(bankNameRef) {
                    top.linkTo(acctNameRef.bottom, 20.dp)
                    start.linkTo(acctNameRef.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, 25.dp)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                },
            text = "United bank For Africa"
        )
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ReceiptUIPreview() {
    ReceiptUI(goToDashboard = {}, downloadReceipt = {})
}