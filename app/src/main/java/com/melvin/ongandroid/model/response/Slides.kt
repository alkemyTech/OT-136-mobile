package com.melvin.ongandroid.model

import com.google.gson.annotations.SerializedName

data class Slides (
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: List<SlidesData>,
    @SerializedName("message") val message: String,
)

data class SlidesData (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("description") val description: String,
)