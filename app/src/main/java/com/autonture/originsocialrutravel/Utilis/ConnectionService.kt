package com.autonture.originsocialrutravel.Utilis

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConnectionService {
    fun service(): ApiService{
        val interceptor = HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
        val client = OkHttpClient.Builder().addInterceptor(interceptor)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://impervm-001-site1.atempurl.com/api/")
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create()).build()
        val apiService  = retrofit.create(ApiService::class.java)
        return apiService
    }
}