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
    fun saveTenant(tenant: TenantEntity)

    @Query("SELECT * FROM TENANT_TABLE ORDER BY id ASC")
    fun getAlLTenants(): Flow<List<TenantEntity>>

    @Query("SELECT * FROM $TENANT_TABLE WHERE id = :id")
    fun getTenant(id: Int): TenantEntity

    @Query("DELETE FROM $TENANT_TABLE WHERE id = :id")
    fun deleteTenant(id: Int)


}