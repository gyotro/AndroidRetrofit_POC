package com.sap.testretrofit.roomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TENANT_TABLE")
data class TenantEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    @ColumnInfo(name = "client_id")
    val clientId: String,
    @ColumnInfo(name = "client_secret")
    val clientSecret: String,
    @ColumnInfo(name = "token_url")
    val urlAuth: String,
    @ColumnInfo(name = "monitor_url")
    val urlMoni: String,
    @ColumnInfo(name = "created_date")
    val date: String,
    val grantType: String = "client_credentials",
    val responseType: String = "token"
)
