package com.melvin.ongandroid.model.repository

import com.melvin.ongandroid.model.response.VerifyUser
import com.melvin.ongandroid.model.service.RetrofitClient
import retrofit2.Response

class UserRepository {
    suspend fun authUser(user: String, pass: String): Response<VerifyUser> {
        return RetrofitClient.retrofitService.postLogin(user, pass)
    }
}