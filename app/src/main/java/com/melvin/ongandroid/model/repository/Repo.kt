package com.melvin.ongandroid.model.repository

import android.content.Context
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.model.New
import com.melvin.ongandroid.model.User
import com.melvin.ongandroid.model.response.Testimonials
import com.melvin.ongandroid.model.response.VerifyUser
import com.melvin.ongandroid.model.service.OnAPIResponse
import retrofit2.Response

interface Repo {
    suspend fun postUser(user:User, context: Context?, onAPIResponse: OnAPIResponse)
    suspend fun postToken(user: String, pass: String): Response<VerifyUser>
    suspend fun getNewsList(newName:String): Resource<List<New>>
    suspend fun getFourTestimonials():Response<Testimonials>
}