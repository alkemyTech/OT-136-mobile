package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.*
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.model.We
import com.melvin.ongandroid.model.repository.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeViewModel(private val repo: Repo) : ViewModel() {

    val listWe = MutableLiveData<Resource<List<We>>>()
    val loading = MutableLiveData<Boolean>()

    fun fetchWeList() {
        viewModelScope.launch {
            loading.value = true
            try {
                listWe.value = repo.getWeList("hola")
                loading.value = false
            }catch (e:Exception){
                listWe.value = Resource.Failure(e)
                loading.value = false
            }
        }
    }
}