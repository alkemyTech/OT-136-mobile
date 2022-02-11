package com.melvin.ongandroid.businesslogic.data
import android.app.AlertDialog
import android.content.Context
import android.provider.Settings.Global.getString
import android.widget.Toast
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.domain.OnRequest
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.businesslogic.vo.RetrofitClient
import com.melvin.ongandroid.model.DefaultResponse
import com.melvin.ongandroid.model.New
import com.melvin.ongandroid.model.User
import com.melvin.ongandroid.model.response.Testimonials
import com.melvin.ongandroid.model.response.VerifyUser
import com.melvin.ongandroid.model.service.OnAPIResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
class DataSource {
    suspend fun postRegister(user: User, context: Context?, onResponse: OnAPIResponse){

        RetrofitClient.retrofitService.createUser(user)
            .enqueue(object: Callback<DefaultResponse>, OnRequest{
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
                    onResponse.onFailure("")
                    Toast.makeText(context,R.string.user_not_register, Toast.LENGTH_LONG).show()

                    if (t is HttpException){
                        when (t.code()) {
                            400 -> dialogBuilder(context, R.string.dataSource_dialogBuilder_title_error,R.string.login_dg_bad_request)
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

    val generateNewsList = listOf(
        New("Llegamos a Tandil", "Con nuestro equipo recorrimos la ciudad y estuvo todo regio", "https://media.ambito.com/p/187dcb184f0b0ecd85f0e24fa8cf979d/adjuntos/239/imagenes/038/230/0038230484/1200x675/smart/tandiljpeg.jpeg"),
        New("Merendamos con los chicxs", "Nos la pasamos dibujando y merendando, estuvo genial", "https://nutricionpereira.com/wp-content/uploads/meriendas-saludables-ninos.jpg"),
        New("Jornada de fútbol", "Nos divertimos mucho practicando nuestro deporte favorito", "https://static.guiainfantil.com/uploads/salud/nino-juega-futbol-pelota-brazo-p.jpg"),
        New("Concurso de dibujo", "Organizamos la primer competencia de dibujo y la ganadora nos cuenta su experiencia", "data:image/jpeg;base64,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")
    )

    fun getNewsList(): Resource<List<New>> {
            return Resource.Success(generateNewsList)
    }

    suspend fun getNewsByName (newName:String):Resource<List<New>>{
        return Resource.Success(RetrofitClient.retrofitService.GetNewsByName(newName).newsList)
    }

    suspend fun getFourTestimonials(): Response<Testimonials> {
        return RetrofitClient.retrofitService.getFourTestimonials()
    }
}