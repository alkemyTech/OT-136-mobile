package com.melvin.ongandroid.businesslogic.data

import android.content.Context
import android.widget.Toast
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.businesslogic.vo.RetrofitClient
import com.melvin.ongandroid.model.DefaultResponse
import com.melvin.ongandroid.model.User
import com.melvin.ongandroid.model.response.VerifyUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class DataSource {

    suspend fun postRegister(user: User, context: Context?): Resource<Call<DefaultResponse>>{

        var ResourceId: Resource<Call<DefaultResponse>> = Resource.Loading()
        RetrofitClient.retrofitService.createUser(user)
            .enqueue(object: Callback<DefaultResponse>{
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Toast.makeText(context,"User was succesfully register",
                        Toast.LENGTH_LONG).show()
                    if(response.isSuccessful){
                        ResourceId=Resource.Success(RetrofitClient.retrofitService.createUser(user))
                    }

                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(context,"The user cannot be registered", Toast.LENGTH_LONG).show()
                    ResourceId= Resource.Failure(Exception())

                }
            })


        return ResourceId

    }

    suspend fun authUser(user: String, pass: String): Response<VerifyUser> {
        return RetrofitClient.retrofitService.postLogin(user, pass)
    }


}
