package com.melvin.ongandroid.businesslogic.data

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.melvin.ongandroid.businesslogic.domain.RestApiService
import com.melvin.ongandroid.businesslogic.vo.MainApplication
import com.melvin.ongandroid.businesslogic.vo.MainApplication.Companion.applicationContext
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.businesslogic.vo.RetrofitClient
import com.melvin.ongandroid.model.DefaultResponse
import com.melvin.ongandroid.model.User
import com.melvin.ongandroid.view.SignUpFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController.getContext

class DataSource {

    suspend fun postRegister(user:User){

        RetrofitClient.retrofitService.createUser(user)
            .enqueue(object: Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Toast.makeText(MainApplication.applicationContext(),"User was succesfully register",Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(MainApplication.applicationContext(),t.message, Toast.LENGTH_LONG).show()
                }

            })

    }

    }
