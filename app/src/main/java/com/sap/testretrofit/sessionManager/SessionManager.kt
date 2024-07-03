package com.sap.cpi_monitor.sessionManager

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.sap.testretrofit.R
import com.sap.testretrofit.TenantDataDefault
import com.sap.testretrofit.data.remote.AuthRepository
import com.sap.testretrofit.sessionManager.TenantData
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class SessionManager(context: Context) : KoinComponent {

    fun getClientId() = prefs.getString(clientId, "")!!
    fun getClientSecret() = prefs.getString(clientSecret, "")!!
    fun getUrlAuth() = prefs.getString(urlAuth, "http://127.0.0.1")!!
    fun getUrlMoni() = prefs.getString(urlMoni, "http://127.0.0.1")!!
    fun getGrantType() = prefs.getString(grant_type, "")!!

    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private var accessTokenExpirationTime: Long = 60 * 60 * 1000 // 60 minutes in milliseconds

    companion object {
        const val USER_TOKEN = "user_token"
        const val clientSecret = "clientSecret"
        const val urlAuth = "urlAuth"
        const val urlMoni = "urlMoni"
        const val clientId = "clientId"
        const val grant_type = "grant_type"
    }

    private var token: String? = null

    // private val scope = CoroutineScope(Dispatchers.IO)
    init {
        Log.d("Session Manager", "Starting Session Manager")
        val editor = prefs.edit()
        editor.putString(clientId, "")
        editor.putString(clientSecret, "")
        editor.putString(urlMoni, "http://127.0.0.1")
        editor.putString(urlAuth, "http://127.0.0.1")
        editor.putString(grant_type, "client_credentials" )
        editor.apply()
    }

    // Method to check if the access token has expired
    fun isAccessTokenExpired(): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        Log.d("Session Manager", "Starting isAccessTokenExpired")
        return currentTimeMillis >= accessTokenExpirationTime
    }

    fun setTenant(tenantData: TenantData) {

        val editor = prefs.edit()
        editor.putString(clientId, tenantData.clientId)
        editor.putString(clientSecret, tenantData.clientSecret)
        editor.putString(urlMoni, tenantData.url)
        editor.putString(urlAuth, tenantData.tokenUrl)
        editor.putString(grant_type, tenantData.grantType)
        editor.apply()
        Log.d("Session Manager", "Initializing tenant data: $tenantData")
    }

    /**
     * Method to update the access token and its expiration time in the session
     */

    suspend fun updateAccessToken() {
        Log.d("Session Manager", "Starting updateAccessToken")
        Log.d("Session Manager", "Client ID: ${getClientId()}")
        Log.d("Session Manager", "Client Secret: ${getClientSecret()}")
        val apiAuth: AuthRepository by inject()
        try {
            token = apiAuth.getToken(
                getClientId(),
                getClientSecret(),
                getGrantType()
/*                TenantDataDefault.clientID,
                TenantDataDefault.clientSecret,
                TenantDataDefault.grant_type*/
            ).accessToken
        } catch (e: Exception) {
            Log.d("Session Manager", "updateAccessToken Error: ${e.printStackTrace()}")
        }
        Log.d("Session Manager", "New token: $token")
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
        accessTokenExpirationTime += System.currentTimeMillis() // Convert expiresIn to milliseconds
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        Log.d("Session Manager", "fetchAuthToken: ${prefs.getString(USER_TOKEN, null)}")
        return prefs.getString(USER_TOKEN, token)
    }
}