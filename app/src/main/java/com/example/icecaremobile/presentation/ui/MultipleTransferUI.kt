package com.example.icecaremobile.presentation.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.icecaremobile.R
import com.example.icecaremobile.presentation.ui.component.AppButton
import com.example.icecaremobile.presentation.ui.component.AppTextField

@Composable
fun MultipleTransferUI(
    modifier: Modifier = Modifier,
    banks: List<String>,
    dollarAmount: (String) -> Unit,
    purpose: (String) -> Unit,
    selectedBanks: (List<String>) -> Unit,
    enteredAmounts: (Map<String, String>) -> Unit,
    receiptUploaded: (List<Uri>) -> Unit,
    onSubmitClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .verticalScroll(scrollState),
    ) {
        Text("Dollar Amount ($)")
        AppTextField(
            enteredValue = dollarAmount,
            label = "",
            startIcon = null,
            endIcon = null,
            keyboardType = KeyboardType.Number
        )

        Spacer(Modifier.height(20.dp))

        Text("Purpose of payment")
        AppTextField(
            enteredValue = purpose,
            label = "",
            startIcon = null,
            endIcon = null,
        )

        Spacer(Modifier.height(20.dp))

        PopulateBanks(
            banks = banks,
            onBankSelectionChanged =
            { selected ->
                selectedBanks(selected)
            },
            onAmountEntered = { amounts ->
                enteredAmounts(amounts)
            }
        )

        ReceiptUploadField(onReceiptsChanged = receiptUploaded)

        Spacer(Modifier.height(50.dp))

        AppButton(
            title = "Submit",
            onClick = onSubmitClick
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PopulateBanks(
    banks: List<String>,
    onBankSelectionChanged: (List<String>) -> Unit,
    onAmountEntered: (Map<String, String>) -> Unit
) {
    val checkedStates = remember { mutableStateListOf<Boolean>().apply { addAll(List(banks.size) { false }) } }
    var checkAllState by remember { mutableStateOf(false) }
    val bankAmounts = remember { mutableStateMapOf<String, String>() }


    // Update selected banks list based on checkbox states
    val selectedBanks = remember {
        derivedStateOf {
            banks.filterIndexed { index, _ -> checkedStates[index] }
        }
    }

    // Notify parent composable when bank selection changes
    LaunchedEffect(selectedBanks.value)
    {
        onBankSelectionChanged(selectedBanks.value)
    }


    Text("Select all banks that transfer involves")

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        banks.forEachIndexed { index, bankName ->
            Row(
                Modifier.wrapContentWidth()
            ) {
                Checkbox(
                    checked = checkedStates[index],
                    onCheckedChange = {
                        checkedStates[index] = it
                        if (!it)
                        {
                            checkAllState = false
                            bankAmounts.remove(bankName)
                        }
                    }
                )

                Text(
                    modifier = Modifier
                        .padding(top = 15.dp),
                    text = bankName,
                    fontSize = 16.sp
                )
            }
        }

        Row(
            Modifier.wrapContentWidth()
        ) {
            Checkbox(
                checked = checkAllState,
                onCheckedChange = { isChecked ->
                    checkAllState = isChecked
                    checkedStates.fill(isChecked)
                }
            )

            Text(
                modifier = Modifier.padding(top = 15.dp),
                text = "Check All",
                fontSize = 16.sp
            )
        }
    }

    Spacer(Modifier.height(10.dp))

    banks.forEachIndexed { index, bankName ->
        if (checkedStates[index]) {
            Text(
                text = "Amount transferred to $bankName",
                modifier = Modifier.padding(vertical = 4.dp)
            )
            AppTextField(
                label = "Enter Amount",
                startIcon = null,
                endIcon = null,
                keyboardType = KeyboardType.Number,
                enteredValue =
                { amount ->
                    if (amount.isNotBlank())
                    {
                        bankAmounts[bankName] = amount
                    }
                    else
                    {
                        bankAmounts.remove(bankName)
                    }
                    onAmountEntered(bankAmounts.toMap())
                }
            )

            Spacer(Modifier.height(20.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ReceiptUploadField(
    onReceiptsChanged: (List<Uri>) -> Unit
) {
    val fileList = remember { mutableStateListOf<Uri>() }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            uri?.let {
                fileList.add(it)
                onReceiptsChanged(fileList.toList()) // Notify parent of the change
            }
        }
    )

    Text(
        modifier = Modifier.padding(bottom = 8.dp),
        text = "Upload all receipts"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 70.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                fileList.forEachIndexed { index, fileUri ->
                    Box(
                        modifier = Modifier
                            .size(120.dp, 120.dp)
                            .background(Color.Gray)
                            .padding(4.dp)
                            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                    ) {
                        if (fileUri.toString().endsWith(".pdf")) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_book),
                                contentDescription = "PDF Preview",
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            AsyncImage(
                                model = fileUri,
                                contentDescription = "Image Preview",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        IconButton(
                            onClick =
                            {
                                fileList.removeAt(index)
                                onReceiptsChanged(fileList.toList())
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .background(Color.Gray, shape = CircleShape)
                                .size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Remove File",
                                tint = Color.White
                            )
                        }
                    }
                }
            }

            Text(
                text = "Upload Receipts",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .wrapContentWidth()
                    .background(Color.Gray, RoundedCornerShape(8.dp))
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable { launcher.launch(arrayOf("image/*", "application/pdf")) },
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MultipleTransferUIPreview() {
    val banks = listOf("Wema Bank", "GTBank", "First Bank")
    MultipleTransferUI(
        banks = banks,
        dollarAmount = {},
        purpose = {},
        selectedBanks = {},
        enteredAmounts = {},
        receiptUploaded = {},
        onSubmitClick = {}
    )
}
