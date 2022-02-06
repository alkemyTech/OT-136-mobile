package com.melvin.ongandroid.businesslogic.data

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.domain.OnRequest
import com.melvin.ongandroid.businesslogic.vo.MainApplication
import com.melvin.ongandroid.businesslogic.vo.RetrofitClient
import com.melvin.ongandroid.model.DefaultResponse
import com.melvin.ongandroid.model.User
import com.melvin.ongandroid.model.response.VerifyUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class DataSource {

    suspend fun postRegister(user: User, context: Context?){

        RetrofitClient.retrofitService.createUser(user)
            .enqueue(object: Callback<DefaultResponse>, OnRequest{
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    dialogBuilder(context,R.string.alert_title,R.string.alert_message)
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    if (t is HttpException){
                        when (t.code()) {
                            400 -> dialogBuilder(context,R.string.dataSource_dialogBuilder_title_error,R.string.login_dg_bad_request)
                            404 -> dialogBuilder(context,R.string.dataSource_dialogBuilder_title_error,R.string.login_dg_resource_not_found)
                            in 500..599 -> dialogBuilder(context,R.string.dataSource_dialogBuilder_title_error,R.string.login_dg_server_error)
                            else -> dialogBuilder(context,R.string.dataSource_dialogBuilder_title_error,R.string.login_dg_unknown_error)
                        }
                    }
                    if (t is IOException) { dialogBuilder(context,R.string.dataSource_dialogBuilder_title_error,R.string.login_dg_without_internet) }
                    if (t is UnknownHostException) {
                        dialogBuilder(context,R.string.dataSource_dialogBuilder_title_error,R.string.login_dg_without_internet)
                    }
                }

                override fun dialogBuilder(context: Context?,title:Int,message:Int) {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle(title)
                    builder.setMessage(message)
                    builder.setPositiveButton(R.string.ok) {
                            dialog, which ->
                    }

                    builder.show()
                }
            })
    }
    suspend fun authUser(user: String, pass: String): Response<VerifyUser> {
        return RetrofitClient.retrofitService.postLogin(user, pass)
    }
}