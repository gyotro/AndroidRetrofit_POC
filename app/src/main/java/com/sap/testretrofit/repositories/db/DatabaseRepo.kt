package com.sap.testretrofit.repositories.db

import com.sap.testretrofit.roomDB.TenantDao
import com.sap.testretrofit.roomDB.TenantEntity
import kotlinx.coroutines.flow.Flow

class DatabaseRepo(private val tenantDao: TenantDao) {
    fun getAll(): Flow<MutableList<TenantEntity>> = tenantDao.getAlLTenants()
    suspend fun getTenant(id: Int): TenantEntity = tenantDao.getTenant(id)
    suspend fun deleteTenant(id: Int) = tenantDao.deleteTenant(id)
    suspend fun saveTenant(tenant: TenantEntity) = tenantDao.saveTenant(tenant)
}