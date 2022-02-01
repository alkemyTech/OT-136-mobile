package com.melvin.ongandroid.businesslogic.vo

import com.google.gson.Gson
import com.melvin.ongandroid.model.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "http://ongapi.alkemy.org/api/"

    private var interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private fun getApi(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(client)
            .build()
    }

    val retrofitService: ApiService by lazy {
        getApi().create(ApiService::class.java)
    }
}