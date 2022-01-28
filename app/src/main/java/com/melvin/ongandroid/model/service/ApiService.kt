package com.melvin.ongandroid.model.service

import com.melvin.ongandroid.model.response.VerifyUser
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("api/login?")

    suspend fun postLogin(
        @Query("email") user: String,
        @Query("password") pass: String
    ): Response<VerifyUser>
}