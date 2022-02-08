package com.melvin.ongandroid.viewmodel

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.melvin.ongandroid.model.repository.Repo
import com.melvin.ongandroid.model.User
import com.melvin.ongandroid.model.service.OnAPIResponse


class SignUpViewModel(private val repo: Repo) : ViewModel() {

    suspend fun postUser(user: User, context: Context?, onAPIResponse: OnAPIResponse) {
        repo.postUser(user, context, onAPIResponse)
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