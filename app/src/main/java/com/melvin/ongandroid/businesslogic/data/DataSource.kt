package com.melvin.ongandroid.businesslogic.data

import android.content.Context
import android.widget.Toast
import com.melvin.ongandroid.businesslogic.vo.RetrofitClient
import com.melvin.ongandroid.model.DefaultResponse
import com.melvin.ongandroid.model.User
import com.melvin.ongandroid.model.response.VerifyUser
import com.melvin.ongandroid.model.service.OnAPIResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataSource {

    suspend fun postRegister(user: User, context: Context?, onResponse: OnAPIResponse){

        RetrofitClient.retrofitService.createUser(user)
            .enqueue(object: Callback<DefaultResponse>{
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Toast.makeText(context,"User was succesfully register",
                        Toast.LENGTH_LONG).show()
                    if(response.isSuccessful){
                        onResponse.onSuccess(response)
                    } else {
                        onResponse.onLoading(response)
                    }

                }
                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    onResponse.onFailure("The user cannot be registered")
                    Toast.makeText(context,"The user cannot be registered", Toast.LENGTH_LONG).show()

                }
            })

    }

    suspend fun authUser(user: String, pass: String): Response<VerifyUser> {
        return RetrofitClient.retrofitService.postLogin(user, pass)
    }


}
