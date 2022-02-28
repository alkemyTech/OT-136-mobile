package com.melvin.ongandroid.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class We(
    @SerializedName("name")
    val name:String,
    @SerializedName("description")
    val descript:String,
    @SerializedName("image")
    val photo: String,
    @SerializedName("facebookUrl")
    val facebook: String,
    @SerializedName("linkedinUrl")
    val linkedin:String
): Parcelable

data class WeList(
    @SerializedName("data")
    val weList: List<We>
)
