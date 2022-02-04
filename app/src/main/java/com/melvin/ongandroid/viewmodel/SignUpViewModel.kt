package com.melvin.ongandroid.viewmodel

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.businesslogic.vo.MainApplication
import com.melvin.ongandroid.businesslogic.vo.MainApplication.Companion.applicationContext
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.model.DefaultResponse
import com.melvin.ongandroid.model.repository.Repo
import com.melvin.ongandroid.model.User
import com.melvin.ongandroid.model.response.VerifyUser
import com.melvin.ongandroid.model.service.OnAPIResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.utf8Size
import retrofit2.Call
import retrofit2.Response
import java.security.AccessController.getContext
import java.util.regex.Matcher

class SignUpViewModel(private val repo: Repo) : ViewModel() {

    suspend fun postUser(user: User, context: Context?, onAPIResponse: OnAPIResponse) {
        repo.postUser(user, context, onAPIResponse)
    }

    val fetchUsers= liveData(Dispatchers.IO) {
        repo.postUser(
            User("Juan","juan@gmail.com", "mjss56"),
            applicationContext(),
            object : OnAPIResponse {
                override fun onSuccess() {
                    TODO("Not yet implemented")
                }

                override fun onFailure(msg: String) {
                    TODO("Not yet implemented")
                }
            }
            )
//        emit(Resource.Loading())
//        try {
//            emit(repo.postUser(User("Juan","juan@gmail.com", "mjss56"), applicationContext()))
//        }catch (e:Exception){
//            emit(Resource.Failure(e))
//        }
    }


    val liveState = MutableLiveData<Boolean>()

    fun checkState(name: String, email: String, password: String, confirmPassword: String) {

        if (!name.isNullOrEmpty()
            && !email.isNullOrEmpty()
            && !password.isNullOrEmpty()
            && !confirmPassword.isNullOrEmpty()
            && verifyEmail(email)
            && verifyPassword(password)
            && verifyConfirmPassword(confirmPassword)
            && password == confirmPassword
        ) {
            liveState.postValue(true)
        } else {
            liveState.postValue(false)
        }
    }

    fun verifyEmail(email: String): Boolean {
       return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun verifyPassword(password: String): Boolean {
        return password.matches("[a-zA-Z0-9]+".toRegex())
    }

    fun verifyConfirmPassword(confirmPassword: String): Boolean {
        return confirmPassword.matches("[a-zA-Z0-9]+".toRegex())
    }
}