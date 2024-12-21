package com.example.icecaremobile.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.icecaremobile.R
import com.example.icecaremobile.core.utils.Helpers
import com.example.icecaremobile.domain.model.Response.TransactionHistory
import com.example.icecaremobile.ui.theme.DarkGolden

@Composable
fun TransactionHistoryUI(
    modifier: Modifier = Modifier,
    searchText: (String) -> Unit,
    onItemSelection: (TransactionHistory) -> Unit,
    historyData: List<TransactionHistory>
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue()) }
    val filteredHistory = remember(historyData, searchQuery.text) {
        historyData.filter { transaction ->
            transaction.category.lowercase().contains(searchQuery.text.lowercase()) ||
                    transaction.description.lowercase().contains(searchQuery.text.lowercase()) ||
                    transaction.totalAmount.lowercase().contains(searchQuery.text.lowercase()) ||
                    transaction.transactionDate.lowercase().contains(searchQuery.text.lowercase())
        }
    }

    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize()
            .padding(bottom = 50.dp)
    ) {

        Text(
            text = "All Transactions",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            placeholder = {
                Text(text = "Search for transaction", fontWeight = FontWeight.Light)
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Handle search action here
                    searchText(searchQuery.text)
                }
            )
        )

        Spacer(Modifier.height(20.dp))

        LazyColumn {
            items(filteredHistory.count()
            ) { index ->
                val item = filteredHistory[index]
                HistoryListItem(
                    headings = item.category,
                    descriptions = item.description,
                    amounts = item.totalAmount,
                    dates = item.transactionDate,
                    onSelect = { onItemSelection(item) }
                )
            }
        }
    }
}

@Composable
fun HistoryListItem(
    headings: String,
    descriptions: String,
    amounts: String,
    dates: String,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 5.dp, bottom = 5.dp)
            .background(color = Color.Gray.copy(0.1f), shape = RoundedCornerShape(8.dp))
            .padding(top = 10.dp, bottom = 10.dp)
            .clickable { onSelect() }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp)
        ) {
            val (image, heading, desc, date, amount) = createRefs()


            Image(
                painter = painterResource(R.drawable.ic_transfer_history),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, margin = 5.dp)
                    },
                colorFilter = ColorFilter.tint(DarkGolden)
            )

            Text(
                text = headings,
                Modifier.constrainAs(heading) {
                    top.linkTo(parent.top, 5.dp)
                    start.linkTo(image.end, margin = 20.dp)
                    end.linkTo(amount.start, margin = 10.dp)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                },
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = Helpers.formatAmountToCurrency(amounts.toDouble()),
                Modifier.constrainAs(amount) {
                    top.linkTo(parent.top, 5.dp)
                    start.linkTo(heading.end, 70.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                },
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = if (amounts.toDouble() < 0) Color.Red else Color.Green,
            )

            Text(
                text = descriptions,
                Modifier.constrainAs(desc) {
                    top.linkTo(heading.bottom, 10.dp)
                    start.linkTo(heading.start)
                    end.linkTo(heading.end)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                },
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = dates,
                Modifier.constrainAs(date) {
                    top.linkTo(amount.bottom, 10.dp)
                    start.linkTo(amount.start)
                    end.linkTo(parent.end, 10.dp)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                },
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TransactionHistoryUIPreview() {
    TransactionHistoryUI(searchText = {}, onItemSelection = {}, historyData = listOf())
}