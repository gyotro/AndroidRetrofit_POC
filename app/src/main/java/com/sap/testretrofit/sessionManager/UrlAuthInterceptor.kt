package com.sap.testretrofit.sessionManager

import android.util.Log
import com.sap.cpi_monitor.sessionManager.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UrlAuthInterceptor : Interceptor, KoinComponent {
    init {
        Log.d("URL_AUTH_INTERCEPTOR","Starting URLAuthInterceptor!")
    }
    private val sessionManager: SessionManager by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("URL_AUTH_INTERCEPTOR","Starting URL interceptor!")
        val originalRequest = chain.request()

        val urlAuth = sessionManager.getUrlAuth()


        // Add the access token to the request header
        val newUrlRequest = originalRequest.newBuilder()
            .url(urlAuth)
            .build()
        Log.d("URL_AUTH_INTERCEPTOR","Exiting...")
        return chain.proceed(newUrlRequest)
    }
}