package com.melvin.ongandroid.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class We(
    @SerializedName("title")
    val title:String,
    @SerializedName("descript")
    val descript:String,
    @SerializedName("photo")
    val photo: String
): Parcelable

data class WeList(
    @SerializedName("results")
    val weList: List<We>
)
