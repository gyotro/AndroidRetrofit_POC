package com.sap.testretrofit.presentation.screen.monitorUI

//import android.R
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sap.testretrofit.data.remote.MessageProcessingLog
import com.sap.testretrofit.presentation.ui.theme.LightGreen
import com.sap.testretrofit.presentation.ui.theme.LightOrange
import com.sap.testretrofit.presentation.ui.theme.LightRed
import com.sap.testretrofit.presentation.ui.theme.LightYellow
import com.sap.testretrofit.utils.FilterBuilder

@SuppressLint("ResourceAsColor")
@Composable
fun CardInterfaceDisplay(
    state: MessageProcessingLog,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when(state.status) {
        "COMPLETED" -> LightGreen
        "FAILED" -> LightRed
        "PROCESSING" -> LightOrange
        "RETRY" -> LightYellow
        else -> Color.Gray
    }
    Card(
        modifier = modifier
            .padding(8.dp)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(15.dp))
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        // elevation = CardDefaults.cardElevation(defaultElevation = 20.dp, focusedElevation = 20.dp),
        shape = RoundedCornerShape(15.dp),
        // border = BorderStroke(2.dp, Color.Black)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = state.integrationFlowName,
                modifier = Modifier.align(alignment = Alignment.Start),
                fontSize = 15.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Start time: " + FilterBuilder.parseStatus(state.logStart),
                modifier = Modifier.align(alignment = Alignment.Start),
                fontSize = 15.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Status: " +state.status,
                modifier = Modifier.align(alignment = Alignment.Start),
                fontSize = 15.sp,
                color = Color.Black
            )
        }
    }
}
