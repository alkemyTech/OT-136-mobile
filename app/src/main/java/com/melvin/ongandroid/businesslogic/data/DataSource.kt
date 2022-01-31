package com.melvin.ongandroid.businesslogic.data

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.domain.OnRequest
import com.melvin.ongandroid.businesslogic.vo.MainApplication
import com.melvin.ongandroid.businesslogic.vo.MainApplication.Companion.applicationContext
import com.melvin.ongandroid.businesslogic.vo.RetrofitClient
import com.melvin.ongandroid.model.DefaultResponse
import com.melvin.ongandroid.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.core.content.ContentProviderCompat.requireContext
import com.melvin.ongandroid.businesslogic.domain.RestApiService
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.view.SignUpFragment
import java.security.AccessController.getContext

class DataSource (){

    suspend fun postRegister(user: User, context: Context?){

        RetrofitClient.retrofitService.createUser(user)
            .enqueue(object: Callback<DefaultResponse>, OnRequest{
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    dialogBuilder(context)
                    Toast.makeText(MainApplication.applicationContext(),"User was succesfully register",Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(MainApplication.applicationContext(),"The user cannot be registered", Toast.LENGTH_LONG).show()
                }

                override fun dialogBuilder(context: Context?) {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle(R.string.alert_title)
                    builder.setMessage(R.string.alert_message)
                    builder.setPositiveButton(R.string.ok) { dialog, which ->
                        //findNavController().navigate(R.id.signInFragment)
                    }

                    builder.show()
                }
            })

    }
}
