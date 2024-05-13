package com.sap.testretrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sap.cpi_monitor.domain.resource.BaseModel
import com.sap.testretrofit.presentation.screen.TokenViewModel
import com.sap.testretrofit.presentation.ui.theme.LightGrey
import com.sap.testretrofit.presentation.ui.theme.TestRetrofitTheme
import com.sap.testretrofit.repositories.FilterBuilder
import com.sap.testretrofit.repositories.Status
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestRetrofitTheme {
                // A surface container using the 'background' color from the theme
                MainScreen()
                }
            }
        }
    }

@Composable
@Preview
fun MainScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.LightGray) {
        val viewModel = koinViewModel<TokenViewModel>()
        InitData(viewModel = viewModel)
    }
}

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun InitData(modifier: Modifier = Modifier, viewModel: TokenViewModel) {
        val interfaces = viewModel.cpiMoniFlow.collectAsState().value

        var top by remember {
            mutableStateOf(setValue("20"))
        }

        var startDate by remember {
            val yesterdayDT = LocalDate.now().minusDays(1).toString()  + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS)
            mutableStateOf(yesterdayDT)
        }

        var nameFlow by remember {
            mutableStateOf("")
        }

        var endDate by remember {
            val currentDT = LocalDate.now().toString() + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS)
            mutableStateOf(currentDT)
        }

        var status = remember {
            mutableListOf(Status.COMPLETED, Status.COMPLETED, Status.RETRY)
        }

        LaunchedEffect(top, startDate, endDate, status) {
            if (top.toInt() > 0 && status.isNotEmpty()) {
                val filterBuilder = FilterBuilder(
                    startDateTime = FilterBuilder.getDateTimeFromString(startDate),
                    endDateTime = FilterBuilder.getDateTimeFromString(endDate),
                    nameFlow = nameFlow,
                    status = status
                )
                viewModel.getMoni2(top.toInt(), filter = filterBuilder)
            } else {
                if (status.isNotEmpty()) {
                    // show popup TODO
                }
                if (top.toInt() <= 0) {
                    // show popup TODO
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cloud Integration Monitoring App.",
                color = Color.DarkGray,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = top,
                    label = {
                        Text("Max Selection")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        top = setValue( it )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.LightGray
                    ),
                    placeholder = {
                        Text("Max Selection")
                    }
                )
            }
            /*       Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LightGrey)
        ) {
            when (interfaces) {
                is BaseModel.Error -> {
                    Text(text = "Base Model Error: ${interfaces.error}")
                }

                BaseModel.Loading -> {
                    //    Text(text = "Base Model Loading")
                    //        LinearProgressIndicator
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is BaseModel.Success -> {
                    LazyColumn {
                        items(interfaces.data.d.results) { item ->
                            Text(text = item.integrationFlowName)
                        }
                    }
                }

                null -> {
                    Text(text = "Null case")
                }
            }
        }*/
        }
    }

fun setValue(it: String): String {
   try {
       if (it.isBlank() ){
           return "0"
       }else
           return it
   }catch (e: Exception) {
       return "0"
   }
}


