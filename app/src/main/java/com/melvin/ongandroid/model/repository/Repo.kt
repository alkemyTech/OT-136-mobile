package com.melvin.ongandroid.model.repository

import android.content.Context
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.model.*
import com.melvin.ongandroid.model.response.Contacts
import com.melvin.ongandroid.model.response.User
import com.melvin.ongandroid.model.response.VerifyUser
import com.melvin.ongandroid.model.service.OnAPIResponse
import retrofit2.Response

interface Repo {
    suspend fun postUser(user: User, context: Context?, onAPIResponse: OnAPIResponse)
    suspend fun postToken(user: String, pass: String): Response<VerifyUser>
    suspend fun getNewsList(newName:String): Resource<List<New>>
    suspend fun getFourTestimonials():Response<Testimonials>
    suspend fun getSlides():Response<Slides>
    suspend fun getWeList(weName:String):Resource<List<We>>
    suspend fun postContact(name: String, phone: String, email: String, message: String): Response<Contacts>

}