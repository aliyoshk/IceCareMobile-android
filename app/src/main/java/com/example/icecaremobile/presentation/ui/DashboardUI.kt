package com.example.icecaremobile.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.icecaremobile.R
import com.example.icecaremobile.ui.theme.AppGolden
import com.example.icecaremobile.ui.theme.LightGolden
import com.example.icecaremobile.ui.theme.LightGray

@Composable
fun DashboardUI(
    modifier: Modifier = Modifier,
    name: String,
    acctNumber: String,
    dollarRate: Double,
    balance: Double,
    onConverterClick: () -> Unit,
    onRemitStatusClick: () -> Unit,
    onTransferStatusClick: () -> Unit,
    onTransferMoneyClick: () -> Unit,
    onTopUpClick: () -> Unit,
    onViewHistoryClick: () -> Unit,
    notification: Boolean = false
){
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(color = LightGolden.copy(alpha = 0.4f))
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .verticalScroll(scrollState)
    ){
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentHeight()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
        )
        {
            Row(
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(50.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Box{
                    Image(
                        painter = painterResource(id = R.drawable.ic_bell),
                        contentDescription = "Logo",
                        modifier = Modifier.size(35.dp)
                    )
                    if (notification){
                        Box(
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .align(Alignment.TopCenter)
                                .size(12.dp)
                                .background(Color.Red, CircleShape)
                        )
                    }
                }
            }

            Text(
                modifier = Modifier.padding(start = 40.dp),
                text = "Ice Care Nig Ltd",
                fontWeight = FontWeight.Thin,
                fontStyle = FontStyle.Italic
            )
        }

        DashboardCard(
            name = name,
            acctNumber = acctNumber,
            dollarRate = dollarRate,
            balance = balance
        )

        Spacer(Modifier.height(10.dp))

        DashboardSection2(onConverterClick, onRemitStatusClick)

        Spacer(Modifier.height(20.dp))

        DashboardSection3(onTransferStatusClick)

        Spacer(Modifier.height(20.dp))

        DashboardSection4(onTransferMoneyClick, onTopUpClick)

        Spacer(Modifier.height(20.dp))

        DashboardSection5(onViewHistoryClick)
    }
}


@Composable
fun DashboardCard(
    name: String,
    acctNumber: String,
    dollarRate: Double,
    balance: Double
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp)
            .graphicsLayer {
                shadowElevation = 6.dp.toPx()
                shape = RoundedCornerShape(35.dp)
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(20.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)
            ) {
                val (nameValue, acctNumberValue,
                    dollarRateText, dollarRateValue,
                    balanceText, balanceValue,
                ) = createRefs()
                val verticalGuideline = createGuidelineFromStart(0.5f)

                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    text = "Hi, $name",
                    modifier = Modifier
                        .constrainAs(nameValue)
                        {
                            start.linkTo(verticalGuideline, margin = 35.dp)
                            start.linkTo(parent.start)
                            top.linkTo(parent.top, margin = 5.dp)
                        }
                )

                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    text = "Dollar Rate",
                    modifier = Modifier
                        .constrainAs(dollarRateText)
                        {
                            top.linkTo(parent.top, margin = 5.dp)
                            start.linkTo(verticalGuideline, margin = 50.dp)
                        },
                )

                Text(
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp,
                    text = acctNumber,
                    modifier = Modifier
                        .constrainAs(acctNumberValue)
                        {
                            top.linkTo(nameValue.bottom, margin = 5.dp)
                            start.linkTo(nameValue.start)
                        }
                )

                Text(
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp,
                    text = "N$dollarRate",
                    modifier = Modifier
                        .constrainAs(dollarRateValue)
                        {
                            top.linkTo(dollarRateText.bottom, margin = 5.dp)
                            start.linkTo(dollarRateText.start)
                        }
                )

                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    text = "Balance",
                    modifier = Modifier
                        .constrainAs(balanceText)
                        {
                            top.linkTo(acctNumberValue.bottom, margin = 20.dp)
                            start.linkTo(acctNumberValue.start)
                        }
                )

                Text(
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp,
                    text = "N$balance",
                    modifier = Modifier
                        .constrainAs(balanceValue)
                        {
                            top.linkTo(balanceText.bottom, margin = 10.dp)
                            start.linkTo(balanceText.start)
                        }
                )
            }

            /*Column(
                modifier = Modifier.wrapContentHeight()
            ) {
                Row {
                    Text(
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        text = "Hi, $name",
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        modifier = Modifier
                            .padding(end = 20.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        text = "Dollar Rate",
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .wrapContentWidth(),
                        fontWeight = FontWeight.Light,
                        fontSize = 18.sp,
                        text = acctNumber,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start),
                        fontWeight = FontWeight.Light,
                        fontSize = 18.sp,
                        text = "N$dollarRate",
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    modifier = Modifier.padding(end = 50.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    text = "Balance",
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    modifier = Modifier.padding(end = 50.dp),
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp,
                    text = "N$balance",
                )
            }*/
        }
    }
}

@Composable
fun DashboardSection2(
    onConverterClick: () -> Unit,
    onRemitStatusClick: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 10.dp, end = 10.dp)
            .border(1.dp, AppGolden, RoundedCornerShape(10.dp))
    ){
        Row(
            modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 10.dp, bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Image(
                painter = painterResource(id = R.drawable.ic_converter),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clickable { onConverterClick() }
            )

            Spacer(Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.ic_remit_status),
                contentDescription = null,
                modifier = Modifier
                    .width(90.dp)
                    .clickable { onRemitStatusClick() }
            )
        }

        Row(
            modifier = Modifier.padding(start = 40.dp, end = 45.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Converter",
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = "Remit Status",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DashboardSection3(onTransferStatusClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onTransferStatusClick() }
    ){
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(R.drawable.bg_status),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp, start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(R.drawable.ic_book),
                contentDescription = null,
                modifier = Modifier
                    .size(45.dp)
            )

            Column(
                modifier = Modifier
                    .padding(start = 10.dp, end = 3.dp)
                    .wrapContentWidth()
            ){
                Text(
                    text = "Transfer Status",
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "Check if your transfer has been confirmed",
                    maxLines = 1
                )
            }

            Image(
                painter = painterResource(R.drawable.ic_arrow_forward),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Black),
                modifier = Modifier
                    .size(20.dp)
                    //.scale(2f)
            )
        }
    }
}

@Composable
fun DashboardSection4(
    onTransferMoneyClick: () -> Unit,
    onTopUpClick: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 10.dp, end = 10.dp)
            .border(1.dp, AppGolden, RoundedCornerShape(10.dp))
    ){
        Row(
            modifier = Modifier.padding(start = 30.dp, end = 45.dp, top = 10.dp, bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Image(
                painter = painterResource(id = R.drawable.ic_transfer),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .clickable { onTransferMoneyClick() }
            )

            Spacer(Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.ic_topup),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .clickable { onTopUpClick() }
            )
        }

        Row(
            modifier = Modifier.padding(start = 25.dp, end = 35.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Transfer Money",
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = "Top Up Account",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DashboardSection5(onViewHistoryClick: () -> Unit)
{
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
    ){
        Text(
            text = "Transaction History",
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(top = 5.dp),
            thickness = 5.dp,
            color = AppGolden
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(LightGray, RectangleShape)
                .clickable { onViewHistoryClick() }
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.Center)
            ){
                Image(
                    painter = painterResource(R.drawable.ic_history),
                    contentDescription = null
                )

                Spacer(Modifier.width(10.dp))

                Text(
                    text = "View your transactions",
                )
            }
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardUIPreview()
{
    DashboardUI(
        name = "Isa",
        acctNumber = "01234567890",
        dollarRate = 1375.54,
        balance = 756983.0,
        notification = true,
        onTransferStatusClick = {},
        onConverterClick = {},
        onTopUpClick = {},
        onTransferMoneyClick = {},
        onRemitStatusClick = {},
        onViewHistoryClick = {}
    )

//    DashboardCard(
//        name = "Isa",
//        acctNumber = "01234567890",
//        dollarRate = 1375.54,
//        balance = 756983.0
//    )
}