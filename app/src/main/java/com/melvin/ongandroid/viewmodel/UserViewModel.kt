package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.model.repository.UserRepository
import com.melvin.ongandroid.model.response.VerifyUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
/*
*
* En caso de que exista un error al llamar al endpoint "api/login" se deberá indicar mediante un modal/dialog el error en cuestión si la respuesta es distinta a un 200.
* En caso de que el error se encuentre dentro de un código 200 indicar en los campos email y password que ha existido un error (ver diseño).
* Al modificarse alguno de ellos, la indicación de error deberá desaparecer de todos los campos.
* */
class UserViewModel : ViewModel() {

    private val userRepository = UserRepository()

    val liveDataUser = MutableLiveData<VerifyUser>()
    val authException = MutableLiveData<Throwable>()

    fun postToken(user: String, pass: String) {
        viewModelScope.launch {
            try {
                val call = userRepository.authUser(user, pass)
                if (call.isSuccessful) {
                    liveDataUser.postValue(call.body())
                }
            } catch(e:Exception) {
                authException.value = e
            }
        }
    }
}