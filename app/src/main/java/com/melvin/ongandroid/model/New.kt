package com.melvin.ongandroid.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class New(
        @SerializedName("name")
        val title:String,
        @SerializedName("content")
        val descript:String,
        @SerializedName("image")
        val photo: String
    ): Parcelable

data class NewsList(
    @SerializedName("data")
    val newsList: List<New>
)