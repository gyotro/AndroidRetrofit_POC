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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sap.cpi_monitor.domain.resource.BaseModel
import com.sap.testretrofit.presentation.screen.CardInterfaceDisplay
import com.sap.testretrofit.presentation.screen.TokenViewModel
import com.sap.testretrofit.presentation.ui.theme.LightBlue
import com.sap.testretrofit.presentation.ui.theme.LightGrey
import com.sap.testretrofit.presentation.ui.theme.TestRetrofitTheme
import com.sap.testretrofit.repositories.FilterBuilder
import com.sap.testretrofit.repositories.Status
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestRetrofitTheme {
                // A surface container using the 'background' color from the theme
                MainScreen()
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
        val yesterdayDate = LocalDate.now().minusDays(1)
        var startDate by remember {
            mutableStateOf(yesterdayDate.toString())
        }

        val currentDate = LocalDate.now()
        var endDate by remember {
            mutableStateOf(currentDate.toString())
        }
        val currentTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS)
        var endTime by remember {
            mutableStateOf(currentTime.toString())
        }

        val startDateState = rememberDatePickerState(
            initialDisplayedMonthMillis = yesterdayDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            initialSelectedDateMillis = yesterdayDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
        var startDateOpened by remember { mutableStateOf(false) }

        val yesterdayTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS)
        var startTime by remember {
            mutableStateOf(yesterdayTime.toString())
        }
        val startTimeState = rememberTimePickerState(
            initialMinute = yesterdayTime.minute,
            initialHour = yesterdayTime.hour
        )
        var startTimeOpened by remember { mutableStateOf(false) }

        var endDateState = rememberDatePickerState(
            initialDisplayedMonthMillis = currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            initialSelectedDateMillis = currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
        var endDateOpened by remember { mutableStateOf(false) }

        var endTimeState = rememberTimePickerState(
            initialMinute = yesterdayTime.minute,
            initialHour = yesterdayTime.hour
        )
        var endTimeOpened by remember { mutableStateOf(false) }

        var status = remember {
            mutableListOf(Status.COMPLETED, Status.FAILED, Status.PROCESSING, Status.RETRY, Status.CANCELED)
        }

        LaunchedEffect(top, startDate, endDate, status, nameFlow) {
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
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
                        top = setValue(it)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.5F)
                        .clip(RoundedCornerShape(8.dp)),
                    value = endDate,
                    readOnly = true,
                    label = {
                        Text("End Date")
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { endDateOpened = true } // show de dialog
                        ) {
                            Icon(imageVector = Icons.Default.DateRange, contentDescription = "Calendar")
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        //  startDate = it
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
                        Text("EndDate")
                    }
                )
                /*IconButton(
                    onClick = {  startDateOpened = true  } // show de dialog
                ) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Calendar")
                }*/
                if (endDateOpened) {
                    DatePickerDialog(
                        onDismissRequest = { endDateOpened = false },
                        confirmButton = {
                            Button(onClick = { endDateOpened = false }) {
                                Text(text = "Confirm")
                            }
                        }) {
                        DatePicker(state = endDateState)
                        endDate =
                            endDateState.selectedDateMillis?.let { FilterBuilder.epochMillisToLocalDate(it) }.toString()
                    }
                }
                Spacer(modifier = Modifier.fillMaxWidth(fraction = 0.1f))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 1F)
                        .clip(RoundedCornerShape(8.dp)),
                    value = endTime,
                    label = {
                        Text("End Time")
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { endTimeOpened = true } // show de dialog
                        ) {
                            Icon(imageVector = Icons.Default.DateRange, contentDescription = "Calendar")
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = {
                        //     startTime = it
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
                if (endTimeOpened) {
                    TimePickerDialog(
                        onCancel = { endTimeOpened = false },
                        onConfirm = { endTimeOpened = false }
                    ) {
                        TimePicker(state = endTimeState)
                        endTime = FilterBuilder.timeFormatter(endTimeState.hour) + ':' + FilterBuilder.timeFormatter(
                            endTimeState.minute
                        ) + ':' + "00"
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.5F)
                        .clip(RoundedCornerShape(8.dp)),
                    value = startDate,
                    readOnly = true,
                    label = {
                        Text("Start Date")
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { startDateOpened = true } // show de dialog
                        ) {
                            Icon(imageVector = Icons.Default.DateRange, contentDescription = "Calendar")
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        //  startDate = it
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
                if (startDateOpened) {
                    DatePickerDialog(
                        onDismissRequest = { startDateOpened = false },
                        confirmButton = {
                            Button(onClick = { startDateOpened = false }) {
                                Text(text = "Confirm")
                            }
                        }) {
                        DatePicker(state = startDateState)
                        startDate = startDateState.selectedDateMillis?.let { FilterBuilder.epochMillisToLocalDate(it) }
                            .toString()
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
                    trailingIcon = {
                        IconButton(
                            onClick = { startTimeOpened = true } // show de dialog
                        ) {
                            Icon(imageVector = Icons.Default.DateRange, contentDescription = "Calendar")
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = {
                        //     startTime = it
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
                if (startTimeOpened) {
                    TimePickerDialog(
                        onCancel = { startTimeOpened = false },
                        onConfirm = { startTimeOpened = false }
                    ) {
                        TimePicker(state = startTimeState)
                        startTime =
                            FilterBuilder.timeFormatter(startTimeState.hour) + ':' + FilterBuilder.timeFormatter(
                                startTimeState.minute
                            ) + ':' + "00"
                    }
                }
            }
 //           Spacer(modifier = Modifier.fillMaxSize(fraction = 0.1f))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LightBlue)
            ) {
                when (interfaces) {
                    is BaseModel.Error -> {
                        Text(text = "Base Model Error: ${interfaces.error}")
                    }

                    is BaseModel.Loading -> {
                        //    Text(text = "Base Model Loading")
                        //        LinearProgressIndicator
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    is BaseModel.Success -> {
                        LazyColumn {
                            items(interfaces.data.d.results) { item ->
                                /*Text(text = item.integrationFlowName)*/
                                CardInterfaceDisplay(state = item)
                            }
                        }
                    }

                    null -> {
                        Text(text = "Null case")
                    }

                }
            }
        }
    }

    fun setValue(it: String): String {
        try {
            if (it.isBlank()) {
                return "0"
            } else
                return it
        } catch (e: Exception) {
            return "0"
        }
    }

    @Composable
    fun TimePickerDialog(
        title: String = "Select Time",
        onCancel: () -> Unit,
        onConfirm: () -> Unit,
        toggle: @Composable () -> Unit = {},
        content: @Composable () -> Unit,
    ) {
        Dialog(
            onDismissRequest = onCancel,
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
        ) {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                tonalElevation = 6.dp,
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .height(IntrinsicSize.Min)
                    .background(
                        shape = MaterialTheme.shapes.extraLarge,
                        color = MaterialTheme.colorScheme.surface
                    ),
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        text = title,
                        style = MaterialTheme.typography.labelMedium
                    )
                    content()
                    Row(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth()
                    ) {
                        toggle()
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(
                            onClick = onCancel
                        ) { Text("Cancel") }
                        TextButton(
                            onClick = onConfirm
                        ) { Text("Accept") }
                    }
                }
            }
        }
    }
}
