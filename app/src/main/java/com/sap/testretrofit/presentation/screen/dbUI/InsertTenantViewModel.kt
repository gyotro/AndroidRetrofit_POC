package com.sap.testretrofit.presentation.screen.dbUI

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.sap.testretrofit.TenantData
import com.sap.testretrofit.repositories.db.DatabaseRepo
import com.sap.testretrofit.roomDB.TenantEntity
import com.sap.testretrofit.roomDB.TenantJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class InsertTenantViewModel(private val repo: DatabaseRepo): ViewModel(), KoinComponent {

    private val _tenantFlow : MutableStateFlow<List<TenantEntity>> = MutableStateFlow(emptyList())
    val tenantFlow = _tenantFlow.asStateFlow()

    private val _singleTenantFlow : MutableStateFlow<TenantEntity?> = MutableStateFlow(null)
    val singleTenantFlow = _singleTenantFlow.asStateFlow()

    init {
        val tenant = TenantEntity(
            name = "Default tenant",
            clientId = TenantData.clientID,
            clientSecret = TenantData.clientSecret,
            urlAuth = TenantData.URL_AUTH,
            urlMoni = TenantData.URL_MONI
        )
        Log.d("TenantViewModel", "init clause")
 //       insertTenant(tenant)
        getAllTenants()
        Log.d("TenantViewModel", "print getAll: ${tenantFlow.value.joinToString()}" )
    }

    fun insertTenant(tenant: TenantEntity) = viewModelScope.launch(Dispatchers.IO) {
        repo.saveTenant(tenant)
    }
    fun getTenant(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        _singleTenantFlow.update { repo.getTenant(id) }
    }
    fun deleteTenant(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteTenant(id)
    }
    fun getAllTenants() = viewModelScope.launch(Dispatchers.IO) {
        repo.getAll().collect { data ->
            _tenantFlow.update { data }
        }
    }
    fun parseJsonKeyAndStoreTenant(name: String, jsonKey: String): Boolean {
        return try {
            val gson: Gson by inject<Gson>()
            Log.d("TenantViewModel", "String to be inserted: $jsonKey")
            val oauthResponse: TenantJson = gson.fromJson(jsonKey, TenantJson::class.java)
            val tenantEntity = TenantEntity(
                name = name,
                clientId = oauthResponse.oauth.clientId,
                clientSecret = oauthResponse.oauth.clientSecret,
                urlAuth = oauthResponse.oauth.tokenUrl,
                urlMoni = oauthResponse.oauth.url
            )
            insertTenant(tenantEntity)
            Log.d("TenantViewModel", "inserting new tenant")
            true
        }catch (e: Exception) {
            Log.d("TenantViewModel", "error inserting new tenant " + e.message)
            false
        }
    }
}