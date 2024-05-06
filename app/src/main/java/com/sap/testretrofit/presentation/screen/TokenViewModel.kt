package com.sap.testretrofit.presentation.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sap.cpi_monitor.sessionManager.SessionManager
import com.sap.testretrofit.TenantData
import com.sap.testretrofit.data.remote.AuthRepository
import com.sap.testretrofit.data.remote.MonitorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TokenViewModel: ViewModel(), KoinComponent {

    private val sessionManager: SessionManager by inject()

    private val apiAuth: AuthRepository by inject<AuthRepository>()
    private val apiCpiMoni: MonitorRepository by inject<MonitorRepository>()

    private val _cpiToken = MutableLiveData("No Data")
    val cpiToken: LiveData<String> get() = _cpiToken

    private val _cpiMoni = MutableLiveData("No Data")
    val cpiMoni: LiveData<String> get() = _cpiMoni

    private val _cpiMoniFlow = MutableStateFlow("no data")
    val cpiMoniFlow = _cpiMoniFlow.asStateFlow()

    init {
        Log.d("ViewModel", "Starting TokenViewModel")
        viewModelScope.launch {
            getMoni()
            Log.d("ViewModel", "getMoni() executed ")
        }
    }



    private suspend fun getAuth() {
        Log.d("ViewModel","clientID: ${TenantData.clientID}")
        Log.d("ViewModel","clientSecret: ${TenantData.clientSecret}")
        _cpiToken.value = apiAuth.getToken(client_id = TenantData.clientID, client_secret = TenantData.clientSecret, grant_type = TenantData.grant_type, response_type = "token").accessToken
       // _cpiToken.value = apiAuth.getTokenBasiAuth().accessToken
    }

    private suspend fun getMoni() {
        val token = sessionManager.updateAccessToken()
        Log.d("ViewModel", "token: $token ")
        _cpiMoniFlow.value = apiCpiMoni.getInterfaces().d.results.joinToString(separator = "---------") { item -> item.integrationFlowName }
        Log.d("ViewModel","ending getMoni")
    }

}
