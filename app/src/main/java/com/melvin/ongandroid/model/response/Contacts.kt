package com.melvin.ongandroid.model.response

import com.google.gson.annotations.SerializedName


data class Contacts (
    @SerializedName("success") val success: Boolean?,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String,
    @SerializedName("message") val message: String,
)
