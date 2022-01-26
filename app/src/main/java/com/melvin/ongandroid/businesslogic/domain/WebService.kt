package com.melvin.ongandroid.businesslogic.domain

import com.melvin.ongandroid.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface WebService {
    @Headers("Content-Type: application/json")
    @POST ("/users")
    suspend fun postUser(@Body user:User): Call<User>
}