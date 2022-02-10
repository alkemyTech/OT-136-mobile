package com.melvin.ongandroid.model.response

import com.google.gson.annotations.SerializedName

data class Testimonials (
    @SerializedName("success") val id: String,
    @SerializedName("data") val data: List<TestimonialData>,
    @SerializedName("message") val message: String,

    )

data class TestimonialData (
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("description") val description: String,
    )