package com.melvin.ongandroid.model.service

import com.melvin.ongandroid.model.*
import com.melvin.ongandroid.model.response.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("register")
    fun createUser(
        @Body user: User
    ): Call<DefaultResponse>

    @POST("login")
    suspend fun postLogin(
        @Query("email") user: String,
        @Query("password") pass: String
    ): Response<VerifyUser>

    @GET("news")
    suspend fun GetNewsByName(@Query("name") newName: String): NewsList

    @GET("testimonials?limit=4")
    suspend fun getFourTestimonials(): Response<Testimonials>

    @GET("testimonials")
    suspend fun getAllTestimonials(): Response<Testimonials>

    @GET("slides")
    suspend fun getSlides(): Response<Slides>

    @GET("members")
    suspend fun GetMembersByName(@Query("name") memberName: String): WeList

    @GET("activities")
    suspend fun getActivities(): Response<Activities>

    @POST ("contacts")

    suspend fun postContact(

        @Query("name") name: String,

        @Query("phone")phone: String,

        @Query("email")email: String,

        @Query("message")message: String,

        ): Response<Contacts>

    @GET("users")
    suspend fun getUserByName(@Query("email") userEmail:String?): UserName
}