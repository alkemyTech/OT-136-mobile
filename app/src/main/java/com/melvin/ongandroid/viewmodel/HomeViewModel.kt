package com.melvin.ongandroid.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.model.repository.Repo
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.model.repository.RepoImpl
import com.melvin.ongandroid.model.Testimonials
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: Repo) : ViewModel() {

    val fetchNewsList= liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getNewsList("hola"))
        }catch (e:Exception){
            emit(Resource.Failure(e))
    
    var testimonials = MutableLiveData<Testimonials>()
    var testimonialsException = MutableLiveData<Throwable>()

    fun getTestimonials(){
        viewModelScope.launch {
            try {
                val call = repo.getFourTestimonials()
                if (call.isSuccessful) {
                    testimonials.value= call.body()
                }
            } catch (e: Exception) {
                testimonialsException.value = e
            }
        }
    }
}