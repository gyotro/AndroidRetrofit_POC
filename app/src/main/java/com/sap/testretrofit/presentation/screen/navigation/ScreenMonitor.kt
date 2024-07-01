package com.sap.testretrofit.presentation.screen.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.sap.testretrofit.presentation.screen.monitorUI.MonitorDataScreen
import com.sap.testretrofit.presentation.screen.monitorUI.MonitorViewModel
import com.sap.testretrofit.roomDB.TenantEntity


class ScreenMonitor(private val viewModel: MonitorViewModel): Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        MonitorDataScreen(modifier = Modifier, viewModel = viewModel, navigator)
    }
}