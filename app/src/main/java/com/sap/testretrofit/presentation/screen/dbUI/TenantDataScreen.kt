package com.sap.testretrofit.presentation.screen.dbUI

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sap.testretrofit.presentation.ui.theme.DarkBlue
import com.sap.testretrofit.presentation.ui.theme.LightGrey
import com.sap.testretrofit.presentation.ui.theme.sap_fiori
import com.sap.testretrofit.presentation.ui.theme.ubuntuFont
import com.sap.testretrofit.roomDB.TenantEntity

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
                ),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(text = "Submit", color = Color.White, fontFamily = sap_fiori)
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
         //       .padding(paddings)
                .fillMaxSize(),
                contentAlignment = Alignment.Center
            )
            {
                AnimatedVisibility(visible = tenants.isEmpty(),
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    Text(text = "Add a Cloud Integration tenant To Monitor",
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold,
                        fontFamily = sap_fiori,
                        fontSize =22.sp)
                }
                AnimatedVisibility(visible = tenants.isNotEmpty(),
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(
                        bottom = paddings.calculateBottomPadding()+8.dp,
                        top = 8.dp,
                        end = 8.dp,
                        start = 8.dp,
                    ),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                        items(
                            items = tenants.sortedBy { it.name },
                            key = { it.id }
                        ){ tenant ->
                            TenantItem(tenant, { }, { viewModel.deleteTenant(tenant.id) })
                        }
                    }
                }
            }
      //      Spacer(modifier = Modifier.weight(1f))

        }
    }


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.TenantItem(tenantEntity: TenantEntity, onClick:() -> Unit, onDelete:() -> Unit){

    Card(modifier = Modifier.fillMaxWidth()
        .background(Color.DarkGray)
        .clickable {
            onClick()
        }
        .animateItemPlacement(
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .padding(
                    horizontal = 8.dp,
                    vertical = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Column {
                    Text(
                        text = tenantEntity.name,
                        fontFamily = sap_fiori,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }
            Box(
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Delete,
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onDelete()
                    })
            }
        }

        Text(
            modifier = Modifier.padding(4.dp),
            text = tenantEntity.date,
            fontFamily = sap_fiori,
            color = Color(0xffebebeb),
            fontSize = 10.sp
        )


    }
}