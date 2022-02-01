package com.melvin.ongandroid.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.melvin.ongandroid.model.repository.Repo
import com.melvin.ongandroid.model.User
import kotlinx.coroutines.launch

class SignUpViewModel(private val repo: Repo): ViewModel() {

     fun postUser (user: User, context: Context?){
         viewModelScope.launch {
             repo.postUser(user, context) }
    }

}