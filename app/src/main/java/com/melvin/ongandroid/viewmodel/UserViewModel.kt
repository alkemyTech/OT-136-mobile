package com.melvin.ongandroid.viewmodel

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.R
import com.melvin.ongandroid.model.repository.Repo
import com.melvin.ongandroid.model.response.VerifyUser
import kotlinx.coroutines.launch

class UserViewModel(private val repo: Repo) : ViewModel() {

    val liveDataUser = MutableLiveData<VerifyUser>()
    val authException = MutableLiveData<Throwable>()
    val validEmail = MutableLiveData<Boolean>()
    val validPassword = MutableLiveData<Boolean>()

    fun postToken(user: String, pass: String) {
        viewModelScope.launch {
            try {
                val call = repo.postToken(user, pass)
                if (call.isSuccessful) {
                    liveDataUser.postValue(call.body())
                }
            } catch(e:Exception) {
                authException.value = e
            }
        }
    }
    fun validateEmailAndPassword(email: String?,password:String?):Boolean {
        validEmail.value = validateEmail(email)
        validPassword.value = validatePassword(password)
        return validEmail.value!! && validPassword.value!!
    }


    fun validateEmail(email: String?):Boolean {
        validEmail.value = android.util.Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()
    }

    fun validatePassword(password:String?):Boolean {
        return if (!password.isNullOrEmpty()) {
            validPassword.value = password.length >= 4
            password.length >= 4
        } else {
            validPassword.value = false
            false
        }

    }
}