package com.melvin.ongandroid.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.melvin.ongandroid.model.repository.UserRepository
import com.melvin.ongandroid.model.response.VerifyUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel:ViewModel() {

    private val userRepository=UserRepository()

    private val liveDataUser= MutableLiveData<VerifyUser>()

    fun postToken(user: String, pass: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = userRepository.authUser(user,pass)
            if(call.isSuccessful){
                liveDataUser.postValue(call.body())
            }
        }
    }
}