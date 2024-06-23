package com.sap.testretrofit.roomDB

import com.google.gson.annotations.SerializedName

data class TenantJson(
    @SerializedName("oauth") val oauth: Oauth
)data class Oauth(
    @SerializedName("clientid") val clientId: String,
    @SerializedName("clientsecret") val clientSecret: String,
    @SerializedName("url") val url: String,
    @SerializedName("createdate") val createDate: String,
    @SerializedName("tokenurl") val tokenUrl: String
)