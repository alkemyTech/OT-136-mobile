package com.melvin.ongandroid.model.response

import com.google.gson.annotations.SerializedName

data class VerifyUser(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: Array<Data>,
    @SerializedName("message") val message: String
)

class Data {
}