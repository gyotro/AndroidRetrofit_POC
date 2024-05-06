package com.sap.testretrofit.data.remote

import retrofit2.http.*

interface AuthRepository {

 //   @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("/oauth/token")
    suspend fun getToken(
        @Field("client_id") client_id: String,
        @Field("client_secret") client_secret: String,
        @Field("grant_type") grant_type: String,
        @Field("response_type") response_type: String = "token"
    ): TokenResponseDto

    @GET("/oauth/token?grant_type=client_credentials")
    @Headers("Authorization: Basic c2ItODRkNTVlNGMtNTk1Zi00NDNjLWFhYzQtMzZmYzA3ZmI2ODRmIWIzMjAxNnxpdCFiMTg2MzE6ZjRjYjQ4ZDQtNDE3Mi00ZGZjLWFiYzAtN2M1YTdkZjBiN2EwJDFlc1gwNy1QMDFiSFNvRmlNbnY5Zmw3RWZTNjRLLWRoUzFoOXNJUmcyWXM9")
    suspend fun getTokenBasiAuth(): TokenResponseDto

}