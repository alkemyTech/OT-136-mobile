package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.model.repository.Repo
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val repo: Repo) : ViewModel() {

    val fetchNewsList= liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getNewsList("hola"))
        }catch (e:Exception){
            emit(Resource.Failure(e))
        }
    }
}