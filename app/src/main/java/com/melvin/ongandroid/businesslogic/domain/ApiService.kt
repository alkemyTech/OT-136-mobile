package com.melvin.ongandroid.businesslogic.domain

import com.melvin.ongandroid.model.DefaultResponse
import com.melvin.ongandroid.model.User
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @POST ("register")
    fun createUser(
       @Body user: User
    ): Call<DefaultResponse>
}