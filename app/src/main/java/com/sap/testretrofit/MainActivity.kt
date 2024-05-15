package com.sap.testretrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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

        var nameFlow by remember {
            mutableStateOf("")
        }

       /* var startDate by remember {
            val yesterdayDT = LocalDate.now().minusDays(1).toString()
            mutableStateOf(yesterdayDT)
        }*/
        var startDate by remember {
            val yesterdayDT = LocalDate.now().minusDays(1).toString()
            mutableStateOf(yesterdayDT)
        }

        var startDateState = rememberDatePickerState()
        var startDateOpened by remember { mutableStateOf(false) }

        var startTime by remember {
            val yesterdayDT = LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString()
            mutableStateOf(yesterdayDT)
        }

        var endDate by remember {
            val currentDT = LocalDate.now().toString()
            mutableStateOf(currentDT)
        }
        var endTime by remember {
            val currentDT = LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString()
            mutableStateOf(currentDT)
        }

        var status = remember {
            mutableListOf(Status.COMPLETED, Status.COMPLETED, Status.RETRY)
        }

        LaunchedEffect(top, startDate, endDate, status) {
            if (top.toInt() > 0 && status.isNotEmpty()) {
                val filterBuilder = FilterBuilder(
                    startDateTime = FilterBuilder.getDateTimeFromString("$startDate $startTime"),
                    endDateTime = FilterBuilder.getDateTimeFromString("$endDate $endTime"),
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
                .padding(horizontal = 10.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
/*            Text(
                text = "Cloud Integration Monitoring App.",
                color = Color.DarkGray,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )*/
      //      Spacer(modifier = Modifier.height(16.dp))
/*            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .clip(RoundedCornerShape(8.dp)),
//                    .background(MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {*/
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.35F)
                            .clip(RoundedCornerShape(8.dp)),
                        value = top,
                        label = {
                            Text("Max Selection")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            top = setValue( it )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Gray,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.LightGray,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.LightGray
                        ),
                        placeholder = {
                            Text("Max Selection")
                        }
                    )
                    Spacer(modifier = Modifier.fillMaxWidth(fraction = 0.1f))
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(fraction = 1F)
                            .clip(RoundedCornerShape(8.dp)),
                        value = nameFlow,
                        label = {
                            Text("Iflow name")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                            nameFlow = it
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Gray,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.LightGray,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.LightGray
                        ),
                        placeholder = {
                            Text("Iflow name")
                        }
                    )
 //               }

            }
     //       Spacer(modifier = Modifier.fillMaxWidth().height(7.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.5F)
                        .clip(RoundedCornerShape(8.dp)),
                    value = startDate,
                    readOnly = true,
                    label = {
                        Text("Start Date")
                    },
                    trailingIcon = { IconButton(
                        onClick = {  startDateOpened = true  } // show de dialog
                    ) {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Calendar")
                    } },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        startDate = it
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Gray,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.LightGray
                    ),
                    placeholder = {
                        Text("StartDate")
                    }
                )
                /*IconButton(
                    onClick = {  startDateOpened = true  } // show de dialog
                ) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Calendar")
                }*/
                if(startDateOpened) {
                    DatePickerDialog(onDismissRequest = { startDateOpened = false },
                        confirmButton = { 
                            Button(onClick = { startDateOpened = false }) {
                                Text(text = "Confirm")
                            }
                        }) {
                        DatePicker(state = startDateState)
                    }
                }
                Spacer(modifier = Modifier.fillMaxWidth(fraction = 0.1f))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 1F)
                        .clip(RoundedCornerShape(8.dp)),
                    value = startTime,
                    label = {
                        Text("Start Time")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = {
                        startTime = it
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Gray,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.LightGray
                    ),
                    placeholder = {
                        Text("Start Time")
                    }
                )

            }
     //       Spacer(modifier = Modifier.fillMaxWidth().height(7.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.5F)
                        .clip(RoundedCornerShape(8.dp)),
                    value = endDate,
                    label = {
                        Text("End Date")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = {
                        endDate = it
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Gray,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.LightGray
                    ),
                    placeholder = {
                        Text("End Date")
                    }
                )
                Spacer(modifier = Modifier.fillMaxWidth(fraction = 0.1f))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 1F)
                        .clip(RoundedCornerShape(8.dp)),
                    value = endTime,
                    label = {
                        Text("End Time")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = {
                        endTime = it
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Gray,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.LightGray
                    ),
                    placeholder = {
                        Text("End Time")
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


