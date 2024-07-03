package com.sap.testretrofit.di

import android.util.Log
import com.google.gson.Gson
import com.sap.testretrofit.sessionManager.HeaderAuthInterceptor
import com.sap.cpi_monitor.sessionManager.SessionManager
import com.sap.testretrofit.TenantDataDefault
import com.sap.testretrofit.sessionManager.UrlAuthInterceptor
import com.sap.testretrofit.data.remote.AuthRepository
import com.sap.testretrofit.data.remote.MonitorRepository
import com.sap.testretrofit.presentation.screen.dbUI.InsertTenantViewModel
import com.sap.testretrofit.presentation.screen.monitorUI.MonitorViewModel
import com.sap.testretrofit.repositories.db.DatabaseRepo
import com.sap.testretrofit.repositories.http.CpiRepo
import com.sap.testretrofit.repositories.http.CpiRepoImpl
import com.sap.testretrofit.roomDB.TenantDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun provideRetrofitBuilder(): Retrofit.Builder {
 //   val networkJson = Json { ignoreUnknownKeys = true }
    Log.d("NetworkDI","Starting provideRetrofitBuilder")
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
//        .addConverterFactory( networkJson.asConverterFactory("application/json".toMediaType()) )
}

fun provideHttpClientBuilder(): OkHttpClient.Builder {
    Log.d("NetworkDI","Starting provideHttpClient")
    return OkHttpClient
        .Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
}

fun provideCPIAuth(builder: Retrofit.Builder, okHttp: OkHttpClient.Builder, sharedPreferences: SessionManager ): AuthRepository {
    val logging = HttpLoggingInterceptor()
    val urlAuthInterceptor = UrlAuthInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    Log.d("NetworkDI","Starting provideCPIAuth")
    return builder
        .baseUrl(TenantDataDefault.URL_AUTH)
        .client(okHttp.addInterceptor(logging)
            .addInterceptor(urlAuthInterceptor)
            .build())
        .build()
        .create(AuthRepository::class.java)
}


/*fun provideHttpClientMoni(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    val authInterceptor = AuthInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    Log.d("NetworkDI","Starting provideHttpClient")
    return OkHttpClient
        .Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(logging)
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()
}*/

fun provideCPIMonitor(builder: Retrofit.Builder, okHttp: OkHttpClient.Builder, sharedPreferences: SessionManager ): MonitorRepository {
    val logging = HttpLoggingInterceptor()
    val authInterceptor = HeaderAuthInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    Log.d("NetworkDI","Starting provideCPIMonitor")
    return builder
   //     .baseUrl(TenantDataDefault.URL_MONI)
        .baseUrl("http://127.0.0.1")
        .client(okHttp.addInterceptor(authInterceptor).addInterceptor(logging).build())
        .build()
        .create(MonitorRepository::class.java)
}

fun repoCPI(CpiApi: MonitorRepository): CpiRepo {
    Log.d("NetworkDI","Starting repoCPI")
    return CpiRepoImpl(api = CpiApi)
}

val gson = module {
    single { Gson() }
}

val databaseModule = module {
    single {
        provideDatabase(get())
    } bind TenantDatabase::class
//    single { provideTenantDao(get()) } bind TenantDao::class
    single { provideTenantRepo(get()) } bind DatabaseRepo::class
    viewModel<InsertTenantViewModel> { InsertTenantViewModel(get()) }
}

val appModule = module {
    single { provideRetrofitBuilder() }
    factory { provideHttpClientBuilder() }
    single { SessionManager( get() ) }
    single { provideCPIAuth(get(), get(), get()) } bind AuthRepository::class
    single { provideCPIMonitor(get(), get(), get()) } bind MonitorRepository::class
    single { repoCPI(get()) } bind CpiRepo::class
    viewModel<MonitorViewModel> { MonitorViewModel(get()) }
    Log.d("DI_Modules","Creating View Model")
}