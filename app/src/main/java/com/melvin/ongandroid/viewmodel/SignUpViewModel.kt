package com.melvin.ongandroid.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.businesslogic.domain.Repo
import com.melvin.ongandroid.model.User
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
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

}