package com.sap.testretrofit.presentation.screen.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.sap.cpi_monitor.sessionManager.SessionManager
import com.sap.testretrofit.presentation.screen.dbUI.InsertTenantViewModel
import com.sap.testretrofit.presentation.screen.dbUI.TenantDataScreen
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ScreenTenants(private val viewModel: InsertTenantViewModel): Screen, KoinComponent {
    @Composable
    override fun Content() {
        val sharedPreferences: SessionManager by inject()
        val navigator = LocalNavigator.current
        TenantDataScreen(viewModel, navigator, sharedPreferences)
    }
}