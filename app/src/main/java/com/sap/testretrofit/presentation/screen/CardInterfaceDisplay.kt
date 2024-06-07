package com.sap.testretrofit.presentation.screen

//import android.R
import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.sap.testretrofit.R
import com.sap.testretrofit.data.remote.MessageProcessingLog
import com.sap.testretrofit.presentation.ui.theme.LightGreen
import com.sap.testretrofit.presentation.ui.theme.LightOrange
import com.sap.testretrofit.presentation.ui.theme.LightRed
import com.sap.testretrofit.presentation.ui.theme.LightYellow
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

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
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 20.dp, focusedElevation = 20.dp),
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(2.dp, Color.Black)
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
                    text = state.logStart,
                    modifier = Modifier.align(alignment = Alignment.Start),
                    fontSize = 15.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = state.status,
                    modifier = Modifier.align(alignment = Alignment.Start),
                    fontSize = 15.sp,
                    color = Color.Black
                )
            }
        }
}

