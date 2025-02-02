package com.example.gooutfit_android.ui.auth.viewmodel

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiAuthConfig {
    companion object{
        fun getApiService(): ApiAuthService {
            val loggingInterceptor =
                if(BuildConfig.DEBUG) {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                } else {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
                }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://identitytoolkit.googleapis.com/v1")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiAuthService::class.java)
        }
    }
}