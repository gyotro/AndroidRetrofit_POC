package com.sap.testretrofit.presentation.screen.dbUI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sap.testretrofit.repositories.db.DatabaseRepo
import com.sap.testretrofit.roomDB.TenantEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InsertTenantViewModel(private val repo: DatabaseRepo): ViewModel() {

    private var _tenantFlow : MutableStateFlow<List<TenantEntity>> = MutableStateFlow(emptyList())
    val tenantFlow = _tenantFlow.asStateFlow()

    private var _singleTenantFlow : MutableStateFlow<TenantEntity?> = MutableStateFlow(null)
    val singleTenantFlow = _singleTenantFlow.asStateFlow()

    fun insertTenant(tenant: TenantEntity) = viewModelScope.launch {
        repo.saveTenant(tenant)
    }
    fun getTenant(id: Int) = viewModelScope.launch {
        _singleTenantFlow.update { repo.getTenant(id) }
    }
    fun deleteTenant(id: Int) = viewModelScope.launch {
        repo.deleteTenant(id)
    }
    fun getAllTenants() = viewModelScope.launch {
        repo.getAll().collect {
            _tenantFlow.update { it }
        }
    }

}