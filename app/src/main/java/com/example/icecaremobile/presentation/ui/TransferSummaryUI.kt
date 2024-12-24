package com.example.icecaremobile.presentation.ui

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.icecaremobile.R
import com.example.icecaremobile.core.utils.Helpers
import com.example.icecaremobile.presentation.ui.component.AppButton
import com.example.icecaremobile.ui.theme.DarkGolden
import com.example.icecaremobile.ui.theme.LightGray

@Composable
fun TransferSummaryUI(
    modifier: Modifier = Modifier,
    amountSent: String,
    dollarEquivalent: String,
    bankName: String,
    accountName: String,
    accountNumber: String,
    date: String,
    uploadedReceipt: (Uri) -> Unit,
    onSubmitClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 50.dp)
    ) {

        var selectedFileUri by remember { mutableStateOf<Uri?>(null) }

        val context = LocalContext.current

        val filePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument(),
            onResult = { uri: Uri? ->
                uri?.let {
                    selectedFileUri = it
                    uploadedReceipt(it)
                } ?: Toast.makeText(context, "No file selected", Toast.LENGTH_SHORT).show()
            }
        )
//        val filePickerLauncher = rememberLauncherForActivityResult(
//            contract = ActivityResultContracts.GetContent()
//        ) { uri: Uri? ->
//            uri?.let {
//                selectedFileUri = it
//                uploadedReceipt(it)
//            } ?: Toast.makeText(context, "No file selected", Toast.LENGTH_SHORT).show()
//        }
        val stroke = Stroke(
            width = 2f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 30.dp, bottom = 40.dp),
            colors = CardDefaults.cardColors(LightGray)
        ){
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)
            ){
                val verticalGuideline = createGuidelineFromStart(0.3f)
                val (
                    amountSentText, amountSentValue,
                    dollarEquivalentText, dollarEquivalentValue,
                    bankNameText, bankNameValue,
                    accountNameText, accountNameValue,
                    accountNumberText, accountNumberValue,
                    dateText, dateValue
                ) = createRefs()

                Text(
                    text = "Amount:",
                    color = DarkGolden,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(amountSentText)
                        {
                            start.linkTo(verticalGuideline, margin = 35.dp)
                            start.linkTo(parent.start)
                            top.linkTo(amountSentValue.top)
                        }
                )

                Text(
                    text = Helpers.formatAmountToCurrency(amountSent.toDouble()),
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(amountSentValue)
                        {
                            top.linkTo(parent.top, margin = 5.dp)
                            start.linkTo(verticalGuideline, margin = 50.dp)
                            end.linkTo(parent.end)
                            width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                        },
                )

                Text(
                    text = "Dollar Equivalent:",
                    color = DarkGolden,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(dollarEquivalentText)
                        {
                            bottom.linkTo(dollarEquivalentValue.bottom)
                            start.linkTo(amountSentText.start)
                        }
                )

                Text(
                    text = Helpers.formatAmountToCurrency(dollarEquivalent.toDouble(), "USD"),
                    modifier = Modifier
                        .constrainAs(dollarEquivalentValue)
                        {
                            top.linkTo(amountSentValue.bottom, margin = 10.dp)
                            start.linkTo(amountSentValue.start)
                            width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                        },
                )

                Text(
                    text = "Bank Name:",
                    color = DarkGolden,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(bankNameText)
                        {
                            top.linkTo(dollarEquivalentValue.bottom, margin = 10.dp)
                            start.linkTo(amountSentText.start)
                        }
                )

                Text(
                    text = bankName,
                    modifier = Modifier
                        .constrainAs(bankNameValue)
                        {
                            top.linkTo(dollarEquivalentValue.bottom, margin = 10.dp)
                            start.linkTo(amountSentValue.start)
                        },
                )

                Text(
                    text = "Account Name:",
                    color = DarkGolden,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(accountNameText)
                        {
                            top.linkTo(bankNameText.bottom, margin = 10.dp)
                            start.linkTo(bankNameText.start)
                        },
                )

                Text(
                    text = accountName,
                    modifier = Modifier
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

                Text(
                    text = "Date:",
                    color = DarkGolden,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(dateText)
                        {
                            top.linkTo(accountNumberText.bottom, margin = 10.dp)
                            start.linkTo(accountNumberText.start)
                        }
                )

                Text(
                    text = date,
                    modifier = Modifier
                        .constrainAs(dateValue)
                        {
                            top.linkTo(accountNumberValue.bottom, margin = 10.dp)
                            start.linkTo(accountNumberValue.start)
                        }
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(30.dp)
                .drawBehind
                {
                    drawRoundRect(
                        color = Color.Gray,
                        style = stroke,
                        cornerRadius = CornerRadius(8.dp.toPx())
                    )
                }
                .clickable {
                    filePickerLauncher.launch(arrayOf("image/*", "application/pdf"))
                    //filePickerLauncher.launch("application/pdf,image/*")
                },
            colors = CardDefaults.cardColors(Color.Transparent)
        ) {
            if (selectedFileUri == null) {
                // Show upload icon if no file is selected
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(40.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_upload),
                        contentDescription = null
                    )
                    Spacer(Modifier.width(30.dp))
                    Text("Upload Evidence of Payment")
                }
            } else {
                // Show file preview if a file is selected
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    // Display the image
                    AsyncImage(
                        model = selectedFileUri,
                        contentDescription = "Image Preview",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentScale = ContentScale.Crop
                    )

                    // Overlay a cancel button at the top-right corner
                    IconButton(
                        onClick = { selectedFileUri = null },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.7f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close, // Use an appropriate close icon
                            contentDescription = "Cancel",
                            tint = Color.White
                        )
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        AppButton(title = "Submit", onClick = {onSubmitClick()})
    }
}



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TransferSummaryUIPreview()
{
    TransferSummaryUI(
        amountSent = "100",
        dollarEquivalent = "100",
        bankName = "Wema Bank Plc",
        accountName = "Ice Care Nig Ltd",
        accountNumber = "0123456789",
        date = "01/01/2023",
        uploadedReceipt = {},
        onSubmitClick = {}
    )
}