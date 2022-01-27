package com.melvin.ongandroid.businesslogic.domain

import com.melvin.ongandroid.businesslogic.vo.RetrofitClient
import com.melvin.ongandroid.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback


class RestApiService {

     /*suspend fun postUser(userData: User, onResult: (User?) -> Unit){
        val retrofit = RetrofitClient.retrofitService(ApiService::class.java)
        retrofit.postUser(userData).enqueue(
            object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<User>, response: Response<User>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }*/
}