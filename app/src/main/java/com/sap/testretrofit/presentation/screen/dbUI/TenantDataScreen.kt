package com.sap.testretrofit.presentation.screen.dbUI

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TenantDataScreen(viewModel: InsertTenantViewModel) {

    val tenants by viewModel.tenantFlow.collectAsState()

    val(dialogOpen, setDialogOpen) = remember {
        mutableStateOf(false)
    }

    if (dialogOpen) {
        val (key, setKey) = remember {
            mutableStateOf("")
        }
        val (name, setName) = remember {
            mutableStateOf("")
        }
        Dialog(onDismissRequest = { setDialogOpen(false) }) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                  OutlinedTextField(
                      value = name,
                      onValueChange = { setName(it) },
                      modifier = Modifier.fillMaxWidth(),
                      maxLines = 1,
                      label = {
                          Text(
                                  text = "Name of the tenant",
                                  color = Color.White
                              )
                      }
                  )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = key,
                    onValueChange = { setKey(it) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 15,
                    label = {
                        Text(
                            text = "Add Json File of Tenant service key",
                            color = Color.White
                        )
                    }
                )
                Spacer(modifier = Modifier.height(18.dp))
                Button(onClick = {
                    if (name.isNotEmpty() && key.isNotEmpty()) {
                        viewModel.parseJsonKeyAndStoreTenant(name, key)
                        setDialogOpen(false)
                    }
                }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
                ) {
                    Text(text = "Submit", color = Color.White)
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.secondary,
        floatingActionButton = {
            FloatingActionButton(onClick = { setDialogOpen(true) }, containerColor = Color.LightGray, contentColor = MaterialTheme.colorScheme.primary) {
                Icon(Icons.Default.Add, contentDescription = "Add Tenant")
            }
        }) { paddings ->
        /*Column(modifier = Modifier
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
            )*/
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
            )
            {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
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
                    }
                    items(tenants){ tenant ->
                        Text(text = tenant.clientId, color = Color.DarkGray,)
                }
            }
      //      Spacer(modifier = Modifier.weight(1f))

        }
    }
}