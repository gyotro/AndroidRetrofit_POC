package com.sap.testretrofit.presentation.screen.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.sap.testretrofit.presentation.screen.dbUI.InsertTenantViewModel
import com.sap.testretrofit.presentation.screen.dbUI.TenantDataScreen


class ScreenTenants(private val viewModel: InsertTenantViewModel): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        TenantDataScreen(viewModel, navigator)
    }
}