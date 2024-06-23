package com.sap.testretrofit.di

import android.content.Context
import androidx.room.Room
import com.sap.testretrofit.repositories.db.DatabaseRepo
import com.sap.testretrofit.roomDB.TenantDao
import com.sap.testretrofit.utils.DB_Constants.TENANT_TABLE
import com.sap.testretrofit.roomDB.TenantDatabase

fun provideDatabase(context: Context): TenantDatabase {
    return Room.databaseBuilder(
        context,
        TenantDatabase::class.java,
        TENANT_TABLE
    ).allowMainThreadQueries()
     .fallbackToDestructiveMigration()
     .build()
}

fun provideTenantDao(database: TenantDatabase): TenantDao = database.tenantDao()

fun provideTenantRepo(dao: TenantDao): DatabaseRepo = DatabaseRepo(dao)