package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.model.repository.Repo
import com.melvin.ongandroid.model.response.Contacts
import kotlinx.coroutines.launch

class ContactViewModel(private val repo: Repo) : ViewModel() {
    val liveDataContact = MutableLiveData<Contacts>()
    var contactException = MutableLiveData<Throwable>()

    fun postContact(name: String, phone: String, email: String, message: String) {
        viewModelScope.launch {
            try {
                val call = repo.postContact(name,phone,email,message)
                if (call.isSuccessful) {
                    liveDataContact.postValue(call.body())
                }
            } catch (e: Exception) {
                contactException.value = e
            }
        }
    }

}
