package com.sap.testretrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.sap.testretrofit.presentation.screen.dbUI.InsertTenantViewModel
import com.sap.testretrofit.presentation.screen.dbUI.TenantDataScreen
import com.sap.testretrofit.presentation.screen.monitorUI.MonitorDataScreen
import com.sap.testretrofit.presentation.screen.monitorUI.TokenViewModel
import com.sap.testretrofit.presentation.ui.theme.TestRetrofitTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestRetrofitTheme {
                ChooseTenantScreen()
                // A surface container using the 'background' color from the theme
                // MonitorScreen()
            }
        }
    }

    @Composable
    @Preview
    fun ChooseTenantScreen() {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.LightGray) {
            val viewModelTenant = koinViewModel<InsertTenantViewModel>()
            TenantDataScreen(viewModel = viewModelTenant)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    @Preview
    fun MonitorScreen() {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.LightGray) {
            val viewModelMonitor = koinViewModel<TokenViewModel>()
            MonitorDataScreen(viewModel = viewModelMonitor)
        }
    }

}


