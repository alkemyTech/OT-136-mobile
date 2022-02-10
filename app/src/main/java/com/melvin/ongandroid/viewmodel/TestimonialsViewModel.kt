package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.melvin.ongandroid.model.response.TestimonialData
import com.melvin.ongandroid.model.response.Testimonials

class TestimonialsViewModel: ViewModel() {
    var testimonials = MutableLiveData<Testimonials>()
    var data = TestimonialData("123","Ana Analia","http://ongapi.alkemy.org/storage/PFiKlzJfNf.jpeg","hola")
    var data2 = TestimonialData("123","Ana Analia","http://ongapi.alkemy.org/storage/PFiKlzJfNf.jpeg","hola")
    var data3 = TestimonialData("123","Ana Analia","http://ongapi.alkemy.org/storage/PFiKlzJfNf.jpeg","hola")
    private var testimonios = listOf(data,data2,data3)

    fun getTestimonials(){
        testimonials.value = Testimonials("success",testimonios,"success")
    }

}