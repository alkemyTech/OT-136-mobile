package com.melvin.ongandroid.model.response

import com.google.gson.annotations.SerializedName

data class VerifyUser(
    @SerializedName("success") val success: Boolean?,
//    @SerializedName("data") val data: Data?,
    @SerializedName("message") val message: String?,
    @SerializedName("error") val messageError: String? // "no token" (si no esta registrado)
)

//class Data {
//}