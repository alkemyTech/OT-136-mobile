package com.melvin.ongandroid.model.service

import com.melvin.ongandroid.model.DefaultResponse
import com.melvin.ongandroid.model.NewsList
import com.melvin.ongandroid.model.User
import com.melvin.ongandroid.model.response.VerifyUser
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @POST ("register")
    fun createUser(
       @Body user: User
    ): Call<DefaultResponse>

    @POST("login")
    suspend fun postLogin(
        @Query("email") user: String,
        @Query("password") pass: String
    ): Response<VerifyUser>

    @GET("news")
    suspend fun GetNewsByName(@Query("name")newName:String):NewsList
}
