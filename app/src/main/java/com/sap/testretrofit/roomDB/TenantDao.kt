package com.sap.testretrofit.roomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sap.testretrofit.utils.DB_Constants.TENANT_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface TenantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTenant(tenant: TenantEntity)

    @Query("SELECT * FROM $TENANT_TABLE ORDER BY id ASC")
    fun getAlLTenants(): Flow<MutableList<TenantEntity>>

    @Query("SELECT * FROM $TENANT_TABLE WHERE id = :id")
    suspend fun getTenant(id: Int): TenantEntity

    @Query("DELETE FROM $TENANT_TABLE WHERE id = :id")
    suspend fun deleteTenant(id: Int)


}