package com.sap.testretrofit.sessionManager

import android.R.attr.host
import android.util.Log
import com.sap.cpi_monitor.sessionManager.SessionManager
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class HeaderAuthInterceptor : Interceptor, KoinComponent {
    init {
        Log.d("AUTH_INTERCEPTOR","Starting AuthInterceptor!")
    }
    private val sessionManager: SessionManager by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("AUTH_INTERCEPTOR","Starting Header interceptor!")
        val originalRequest = chain.request()


        val accessToken = sessionManager.fetchAuthToken()
        val urlMoni = sessionManager.getUrlMoni().replace("https://","")

        val reqHost = originalRequest.url.host

        Log.d("AUTH_INTERCEPTOR","Old Host: $reqHost")

        val newUrl: HttpUrl = originalRequest.url.newBuilder()
            .scheme("https")
            .host(urlMoni)
            .build()

        Log.d("AUTH_INTERCEPTOR","New Host: $newUrl")

        // Add the access token to the request header
        val authorizedRequest = originalRequest.newBuilder()
            .url(newUrl)
            .header("Authorization", "Bearer $accessToken")
            .build()
        Log.d("AUTH_INTERCEPTOR","Using Token: $accessToken")
        return chain.proceed(authorizedRequest)
    }
}