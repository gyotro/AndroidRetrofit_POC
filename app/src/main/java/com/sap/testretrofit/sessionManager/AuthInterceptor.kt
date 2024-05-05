package com.sap.cpi_monitor.sessionManager

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthInterceptor : Interceptor, KoinComponent {
    init {
        Log.d("AUTH_INTERCEPTOR","Starting AuthInterceptor!")
    }
    private val sessionManager: SessionManager by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("AUTH_INTERCEPTOR","Starting intercept!")
        val originalRequest = chain.request()
      //  val requestBuilder = chain.request().newBuilder()

/*        // If token has been saved, add it to the request
        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }*/

        val accessToken = sessionManager.fetchAuthToken()

       /* if (accessToken == null || sessionManager.isAccessTokenExpired()) {

            // Make the token refresh request

            val refreshedToken = CoroutineScope(context = Dispatchers.Main).launch {
                try {
                    val response = authRepo.getToken(TenantData.clientID, TenantData.clientSecret, TenantData.grant_type)
                    Log.d("AUTH_INTERCEPTOR","Trying to get new token!")

                    // Update the refreshed access token and its expiration time in the session
                    response.let { sessionManager.updateAccessToken(it.accessToken ) }
                    Log.d("AUTH_INTERCEPTOR","Token: ${sessionManager.fetchAuthToken()}")

                    sessionManager.fetchAuthToken()
                }catch (e: Exception)
                {
                    e.printStackTrace()
                }


            }
            Log.d("AUTH_INTERCEPTOR","Refresh Token: ${refreshedToken}")

                // Create a new request with the refreshed access token
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $refreshedToken")
                    .build()

                // Retry the request with the new access token
                return chain.proceed(newRequest)

        }
*/
        // Add the access token to the request header
        val authorizedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()
        Log.d("AUTH_INTERCEPTOR","Using Token: $accessToken")
        return chain.proceed(authorizedRequest)
    }
}