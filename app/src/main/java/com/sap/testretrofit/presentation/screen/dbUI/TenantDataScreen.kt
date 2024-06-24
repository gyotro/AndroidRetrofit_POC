package com.sap.testretrofit.presentation.screen.dbUI

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TenantDataScreen(viewModel: InsertTenantViewModel) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Text(text = "Add or Choose a Cloud Integration tenant To Monitor",
                modifier =Modifier.padding(10.dp),
                color = Color.DarkGray,
                fontSize = TextUnit.Unspecified,
//                fontStyle = null,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
//                //  letterSpacing =,
//                textDecoration = null,
                textAlign = TextAlign.Center,
//                //  lineHeight =,
//                // overflow =,
//                softWrap = false,
//                maxLines = 0,
//                minLines = 0,
//                //   onTextLayout = { -> },
//                style = TextStyle.Default.fontStyle
            )
            Box(modifier = Modifier.fillMaxWidth()){

            }
            Spacer(modifier = Modifier.weight(1f))
            ElevatedButton(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Add Tenant")
            }
        }
    }
}