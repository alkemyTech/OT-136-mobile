package com.melvin.ongandroid.businesslogic.data

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.domain.OnRequest
import com.melvin.ongandroid.businesslogic.vo.MainApplication
import com.melvin.ongandroid.businesslogic.vo.RetrofitClient
import com.melvin.ongandroid.model.DefaultResponse
import com.melvin.ongandroid.model.User
import com.melvin.ongandroid.view.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class DataSource (){
    var navController: NavController? = null


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

                override fun dialogBuilder(context: Context?)  {

                    val builder = AlertDialog.Builder(context)
                    builder.setTitle(R.string.alert_title)
                    builder.setMessage(R.string.alert_message)
                    builder.setPositiveButton(R.string.ok) {
                            dialog, which ->

                    }

                    builder.show()

                }




            })

    }
}
