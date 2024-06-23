package com.sap.testretrofit.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TenantEntity::class], version = 1)
abstract class TenantDatabase: RoomDatabase() {
    abstract fun tenantDao(): TenantDao

}