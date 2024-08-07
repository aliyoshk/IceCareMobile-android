package com.example.icecaremobile.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.icecaremobile.R
import com.example.icecaremobile.presentation.ui.component.AppButton
import com.example.icecaremobile.ui.theme.DarkGolden
import com.example.icecaremobile.ui.theme.LightGolden
import com.example.icecaremobile.ui.theme.LightGray


@Composable
fun RemitStatusUI(
    modifier: Modifier = Modifier,
    isSuccess: Boolean = false,
    successUnderlineText: String = "Download Documents",
    btnProceed: () -> Unit,
    onDownloadClick: () -> Unit
) {
    val scroll = rememberScrollState()
    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize()
            .verticalScroll(scroll),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        Image(
            painter = painterResource(if (isSuccess) R.drawable.ic_success else R.drawable.ic_pending),
            contentDescription = null,
            modifier = Modifier
                .background(LightGolden.copy(alpha = 0.3f), RoundedCornerShape(5.dp))
                .padding(15.dp)
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = if (isSuccess) "Success!" else "Thank You! ",
            color = Color.Red,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(10.dp))

        Text(text = if (isSuccess) "Your transaction was completed." else "Your transaction is being processed.")

        Spacer(Modifier.height(50.dp))

        if (isSuccess){
            Text(
                text = successUnderlineText,
                color = DarkGolden,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier.clickable { onDownloadClick() }
            )
        }
        else {
            Text(
                textAlign = TextAlign.Center,
                letterSpacing = 2.sp,
                lineHeight = 18.sp,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .background(LightGray.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                    .padding(top = 20.dp, bottom = 20.dp, start = 10.dp, end = 10.dp),
                color = Color.Black.copy(alpha = 0.7f),
                text = "Once remittance is complete, you will be redirected to view and download transaction related documents."
            )
        }


        Spacer(Modifier.height(80.dp))

        AppButton(
            title = "Go to Dashboard",
            onClick = { btnProceed() }
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RemitStatusUIPreview() {
    RemitStatusUI(
        isSuccess = false,
        btnProceed = {},
        onDownloadClick = {}
    )
}