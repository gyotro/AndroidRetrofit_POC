package com.sap.testretrofit.sessionManager

data class TenantData(
    val clientId: String,
    val clientSecret: String,
    val url: String,
    val tokenUrl: String)