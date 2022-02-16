package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.model.repository.Repo
import kotlinx.coroutines.Dispatchers

class WeViewModel(private val repo: Repo) : ViewModel() {

    val fetchWeList= liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getWeList("hola"))
        }catch (e:Exception){
            emit(Resource.Failure(e))
        }
    }
}