package com.melvin.ongandroid.model.repository

import android.content.Context
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.model.DefaultResponse
import com.melvin.ongandroid.model.User
import com.melvin.ongandroid.model.response.VerifyUser
import retrofit2.Call
import retrofit2.Response

interface Repo {
    suspend fun postUser(user:User, context: Context?): Resource<Call<DefaultResponse>>
    suspend fun postToken(user: String, pass: String): Response<VerifyUser>
}