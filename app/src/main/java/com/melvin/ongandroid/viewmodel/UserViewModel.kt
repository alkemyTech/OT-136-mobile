package com.melvin.ongandroid.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.model.repository.Repo
import com.melvin.ongandroid.model.response.VerifyUser
import kotlinx.coroutines.launch
class UserViewModel(private val repo: Repo) : ViewModel() {

    val liveDataUser = MutableLiveData<VerifyUser>()
    val authException = MutableLiveData<Throwable>()

    fun postToken(user: String, pass: String) {
        viewModelScope.launch {
            try {
                val call = repo.postToken(user, pass)
                if (call.isSuccessful) {
                    liveDataUser.postValue(call.body())
                }
            } catch (e: Exception) {
                authException.value = e
            }
        }
    }
}