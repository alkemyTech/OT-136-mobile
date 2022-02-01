package com.melvin.ongandroid.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.melvin.ongandroid.businesslogic.domain.Repo
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(private val repo: Repo): ViewModel() {

     fun postUser (user: User, context: Context?){
         viewModelScope.launch {
             repo.postUser(user, context) }
    }

    val liveState = MutableLiveData<Boolean>()

    fun checkState(name:String,email:String,password:String,confirmPassword:String){

        if(!name.isNullOrEmpty() && !email.isNullOrEmpty() && !password.isNullOrEmpty()
            && !confirmPassword.isNullOrEmpty()){
            liveState.postValue(true)
        }else{
            liveState.postValue(false)
        }
    }
}