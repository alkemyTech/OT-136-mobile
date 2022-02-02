package com.melvin.ongandroid.model.repository

import android.content.Context
import com.melvin.ongandroid.model.User
import com.melvin.ongandroid.model.response.VerifyUser
import retrofit2.Response

interface Repo {
    suspend fun postUser(user:User, context: Context?)
    suspend fun postToken(user: String, pass: String): Response<VerifyUser>
}