package com.sap.testretrofit.repositories.db

import com.sap.testretrofit.roomDB.TenantDao
import com.sap.testretrofit.roomDB.TenantDatabase
import com.sap.testretrofit.roomDB.TenantEntity
import kotlinx.coroutines.flow.Flow

class DatabaseRepoImpl(private val database: TenantDatabase): DatabaseRepo {
    private val tenantDao = database.tenantDao()
    override suspend fun getAll(): Flow<List<TenantEntity>> = tenantDao.getAlLTenants()
    override suspend fun getTenant(id: Int): TenantEntity = tenantDao.getTenant(id)
    override suspend fun deleteTenant(id: Int) = tenantDao.deleteTenant(id)
    override suspend fun saveTenant(tenant: TenantEntity) = tenantDao.saveTenant(tenant)
}