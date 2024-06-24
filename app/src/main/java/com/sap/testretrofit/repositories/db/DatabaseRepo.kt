package com.sap.testretrofit.repositories.db

import com.sap.testretrofit.roomDB.TenantEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseRepo {
    suspend fun getAll(): Flow<List<TenantEntity>>
    suspend fun getTenant(id: Int): TenantEntity
    suspend fun deleteTenant(id: Int)
    suspend fun saveTenant(tenant: TenantEntity)
}