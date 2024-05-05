package com.sap.testretrofit.data.remote
import com.google.gson.annotations.SerializedName

//import kotlinx.serialization.SerialName
//import kotlinx.serialization.Serializable

//@Serializable
data class TokenResponseDto(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("expires_in") val expiresIn: Int,
    val jti: String,
    val scope: String,
    @SerializedName("token_type") val tokenType: String
)