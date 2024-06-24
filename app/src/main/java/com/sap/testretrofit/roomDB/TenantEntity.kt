package com.sap.testretrofit.roomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sap.testretrofit.utils.DB_Constants.TENANT_TABLE
import java.time.LocalDate

@Entity(tableName = TENANT_TABLE)
data class TenantEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
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
    val date: String = LocalDate.now().toString(),
    val grantType: String = "client_credentials",
    val responseType: String = "token"
)
